package tw.edu.chit.struts.action.secretary;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import tw.edu.chit.service.AdminManager;
import tw.edu.chit.struts.action.BaseLookupDispatchAction;
import tw.edu.chit.util.IConstants;
import tw.edu.chit.util.Parameters;
import tw.edu.chit.util.Toolket;

import com.google.gdata.data.DateTime;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.batch.BatchOperationType;
import com.google.gdata.data.batch.BatchStatus;
import com.google.gdata.data.batch.BatchUtils;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.data.extensions.Recurrence;
import com.google.gdata.data.extensions.When;
import com.google.gdata.data.extensions.Where;
import com.google.gdata.util.AuthenticationException;

public class CalendarAction extends BaseLookupDispatchAction {

	private static String CALENDAR_EVENTS = "calendarEvents";
	private static String CALENDAR_CODE_INFO = "CalCodeInfo";

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute(CALENDAR_EVENTS);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		Toolket.resetCheckboxCookie(response, CALENDAR_CODE_INFO);
		DateFormat df = new SimpleDateFormat("yyyy年M月d日", Locale.TAIWAN);
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();

		Calendar now = Calendar.getInstance();
		aForm.set("fromYears", Toolket.getYearArray(String.valueOf(now
				.get(Calendar.YEAR)), 2));
		aForm.set("toYears", Toolket.getYearArray(String.valueOf(now
				.get(Calendar.YEAR)), 2));
		aForm.set("fromYear", String.valueOf(now.get(Calendar.YEAR)));
		aForm.set("fromMonth", String.valueOf(now.get(Calendar.MONTH) + 1));
		aForm.set("fromDays", Toolket.getDayOfMonthArray(now));
		aForm.set("fromDay", "1");

		Calendar from = (Calendar) now.clone();
		from.set(Calendar.DAY_OF_MONTH, from
				.getActualMinimum(Calendar.DAY_OF_MONTH));
		from.set(Calendar.HOUR_OF_DAY, 0);
		from.set(Calendar.MINUTE, 0);
		from.set(Calendar.SECOND, 0);
		from.set(Calendar.MILLISECOND, 0);
		session.setAttribute("fromDate", df.format(from.getTime()));

		// now.add(Calendar.DAY_OF_MONTH, 7);
		now.set(Calendar.DAY_OF_MONTH, from.getActualMaximum(Calendar.DAY_OF_MONTH));
		aForm.set("toYear", String.valueOf(now.get(Calendar.YEAR)));
		aForm.set("toMonth", String.valueOf(now.get(Calendar.MONTH) + 1));
		aForm.set("toDays", Toolket.getDayOfMonthArray(now));
		aForm.set("toDay", String.valueOf(now.get(Calendar.DAY_OF_MONTH)));

		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		session.setAttribute("toDate", df.format(now.getTime()));

		now.add(Calendar.DAY_OF_MONTH, 1);
		DateTime rangeFrom = Toolket.parseDateToGoogleDateTime(from.getTime());
		DateTime rangeTo = Toolket.parseDateToGoogleDateTime(now.getTime());

		try {
			IConstants.GOOGLE_SERVICES.setUserCredentials(
					IConstants.GOOGLE_EMAIL_USERNAME,
					IConstants.GOOGLE_EMAIL_PASSWORD);
		} catch (AuthenticationException ae) {
			log.error(ae.getMessage(), ae);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "遠端認證資訊錯誤,請稍後再試,或電洽電算中心人員,謝謝!"));
			saveMessages(request, messages);
		}

		CalendarEventEntry[] entries = am.findCalendarEventBy(
				IConstants.GOOGLE_SERVICES, rangeFrom, rangeTo);
		List<DynaBean> beans = new LinkedList<DynaBean>();
		List<ExtendedProperty> props = null;
		if (entries.length != 0) {
			DynaBean bean = null;
			for (CalendarEventEntry entry : entries) {
				props = entry.getExtendedProperty();
				bean = new LazyDynaBean();
				bean.set("title", entry.getTitle().getPlainText());
				if (StringUtils.contains(entry.getPlainTextContent(), "主持人"))
					bean.set("content", StringUtils.abbreviate(StringUtils
							.substringAfter(entry.getPlainTextContent().trim(),
									"\n"), 20));
				else
					bean.set("content", StringUtils.abbreviate(entry
							.getPlainTextContent().trim(), 20));
				bean.set("edited", entry.getEdited().toUiString());
				bean.set("published", entry.getPublished().toUiString());
				bean.set("location", StringUtils.defaultIfEmpty(entry
						.getLocations().get(0).getValueString(), ""));
				bean.set("startTime", entry.getTimes().get(0).getStartTime()
						.toUiString());
				bean.set("endTime", entry.getTimes().get(0).getEndTime()
						.toUiString());
				bean.set("oid", entry.getTimes().get(0).getStartTime()
						.toUiString().split(" ")[0].replaceAll("-", "")
						+ "."
						+ StringUtils.substringAfterLast(entry.getId(), "/"));

				props = entry.getExtendedProperty();
				for (ExtendedProperty prop : props) {
					if ("host".equalsIgnoreCase(prop.getName()))
						bean.set("host", StringUtils.trimToEmpty(prop
								.getValue()));
				}

				beans.add(bean);
			}
		}

		session.setAttribute(CALENDAR_EVENTS, beans);
		setContentPage(session, "secretary/Calendar.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	/**
	 * 
	 * 
	 * @param mapping org.apache.struts.action.ActionMapping object
	 * @param form org.apache.struts.action.ActionForm object
	 * @param request javax.servlet.http.HttpServletRequest object
	 * @param response javax.servlet.http.HttpServletResponse object
	 * @return org.apache.struts.action.ActionForward object
	 * @exception java.lang.Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		session.removeAttribute(CALENDAR_EVENTS);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		Toolket.resetCheckboxCookie(response, CALENDAR_CODE_INFO);
		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();

		String fromYear = aForm.getString("fromYear");
		String fromMonth = aForm.getString("fromMonth");
		String fromDay = aForm.getString("fromDay");

		String toYear = aForm.getString("toYear");
		String toMonth = aForm.getString("toMonth");
		String toDay = aForm.getString("toDay");

		Date from = java.sql.Date.valueOf(fromYear + "-" + fromMonth + "-"
				+ fromDay);
		Date to = java.sql.Date.valueOf(toYear + "-" + toMonth + "-" + toDay);
		Calendar cal = Calendar.getInstance();
		cal.setTime(to);
		cal.add(Calendar.DAY_OF_MONTH, 1);

		try {
			IConstants.GOOGLE_SERVICES.setUserCredentials(
					IConstants.GOOGLE_EMAIL_USERNAME,
					IConstants.GOOGLE_EMAIL_PASSWORD);
		} catch (AuthenticationException ae) {
			log.error(ae.getMessage(), ae);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "遠端認證資訊錯誤,請稍後再試,或電洽電算中心人員,謝謝!"));
			saveMessages(request, messages);
		}

		DateTime rangeFrom = Toolket.parseDateToGoogleDateTime(from);
		DateTime rangeTo = Toolket.parseDateToGoogleDateTime(cal.getTime());

		CalendarEventEntry[] entries = am.findCalendarEventBy(
				IConstants.GOOGLE_SERVICES, rangeFrom, rangeTo);
		List<DynaBean> beans = new LinkedList<DynaBean>();
		List<ExtendedProperty> props = null;
		if (entries.length != 0) {
			DynaBean bean = null;
			for (CalendarEventEntry entry : entries) {
				bean = new LazyDynaBean();
				bean.set("title", entry.getTitle().getPlainText());
				if (StringUtils.contains(entry.getPlainTextContent(), "主持人"))
					bean.set("content", StringUtils.abbreviate(StringUtils
							.substringAfter(entry.getPlainTextContent().trim(),
									"\n"), 20));
				else
					bean.set("content", StringUtils.abbreviate(entry
							.getPlainTextContent().trim(), 20));
				bean.set("edited", entry.getEdited().toUiString());
				bean.set("published", entry.getPublished().toUiString());
				bean.set("location", StringUtils.defaultIfEmpty(entry
						.getLocations().get(0).getValueString(), ""));
				bean.set("startTime", entry.getTimes().get(0).getStartTime()
						.toUiString());
				bean.set("endTime", entry.getTimes().get(0).getEndTime()
						.toUiString());
				bean.set("oid", entry.getTimes().get(0).getStartTime()
						.toUiString().split(" ")[0].replaceAll("-", "")
						+ "."
						+ StringUtils.substringAfterLast(entry.getId(), "/"));

				props = entry.getExtendedProperty();
				for (ExtendedProperty prop : props) {
					if ("host".equalsIgnoreCase(prop.getName()))
						bean.set("host", StringUtils.trimToEmpty(prop
								.getValue()));
				}

				beans.add(bean);
			}
		}

		session.setAttribute(CALENDAR_EVENTS, beans);
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward chooseAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		((DynaActionForm) form).initialize(mapping);
		setContentPage(request.getSession(false), "secretary/CalendarAdd.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaActionForm aForm = (DynaActionForm) form;
		ActionMessages messages = new ActionMessages();

		String title = aForm.getString("title").trim();
		String content = aForm.getString("content").trim();
		String location = StringUtils.defaultIfEmpty(aForm
				.getString("location"), "");
		Date startDate = Toolket.parseNativeDate(request
				.getParameter("startDate"));
		String startHour = aForm.getString("startTime");
		String startMin = aForm.getString("startMin");
		String range = aForm.getString("range");
		String host = StringUtils.trimToNull(aForm.getString("host"));
		String recurData = "DTSTART;VALUE=DATE:20070501\r\n"
				+ "DTEND;VALUE=DATE:20070502\r\n"
				+ "RRULE:FREQ=WEEKLY;BYDAY=Tu;UNTIL=20070904\r\n";
		recurData = null;

		try {
			IConstants.GOOGLE_SERVICES.setUserCredentials(
					IConstants.GOOGLE_EMAIL_USERNAME,
					IConstants.GOOGLE_EMAIL_PASSWORD);
		} catch (AuthenticationException ae) {
			log.error(ae.getMessage(), ae);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "遠端認證資訊錯誤,請稍後再試,或電洽電算中心人員,謝謝!"));
			saveMessages(request, messages);
		}

		try {

			CalendarEventEntry entry = new CalendarEventEntry();
			entry.setTitle(new PlainTextConstruct(title));
			StringBuilder builder = new StringBuilder();
			if (StringUtils.isNotBlank(host))
				builder.append("主持人:").append(host).append("\n");
			builder.append(content);
			entry.setContent(new PlainTextConstruct(builder.toString()));
			entry.setQuickAdd(false);
			// entry.setWebContent(wc);
			if (StringUtils.isNotBlank(location))
				entry.addLocation(new Where("", "", location));

			if (StringUtils.isNotBlank(host)) {
				ExtendedProperty property = new ExtendedProperty();
				property.setName("host");
				property.setValue(host);
				property.setRealm("CIS");
				entry.addExtendedProperty(property);
			}

			if (StringUtils.isNotBlank(recurData)) {
				Recurrence recur = new Recurrence();
				recur.setValue(recurData);
				entry.setRecurrence(recur);
			} else {
				Calendar cal = Calendar.getInstance();
				cal.setTime(startDate);
				cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startHour));
				cal.set(Calendar.MINUTE, Integer.parseInt(startMin));
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				DateTime startTime = new DateTime(cal.getTime(), TimeZone
						.getDefault());

				cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(range));
				DateTime endTime = new DateTime(cal.getTime(), TimeZone
						.getDefault());
				When eventTimes = new When();
				eventTimes.setStartTime(startTime);
				eventTimes.setEndTime(endTime);
				entry.addTime(eventTimes);
			}

			IConstants.GOOGLE_SERVICES.insert(Parameters.GOOGLE_EVENT_FEED_URL,
					entry);

			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "新增成功!"));
			saveMessages(request, messages);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "新增失敗!"));
			saveMessages(request, messages);
		}

		return unspecified(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		String[] oids = Toolket.getSelectedIndexFromCookie(request,
				CALENDAR_CODE_INFO).split("\\|");
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		ActionMessages messages = new ActionMessages();
		Date d = null;
		DateTime from = null, to = null;
		CalendarEventEntry[] entries = null;
		List<CalendarEventEntry> delEntries = new LinkedList<CalendarEventEntry>();
		String id = null;
		CalendarEventEntry toDelEntry = null;

		try {
			IConstants.GOOGLE_SERVICES.setUserCredentials(
					IConstants.GOOGLE_EMAIL_USERNAME,
					IConstants.GOOGLE_EMAIL_PASSWORD);
		} catch (AuthenticationException ae) {
			log.error(ae.getMessage(), ae);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "遠端認證資訊錯誤,請稍後再試,或電洽電算中心人員,謝謝!"));
			saveMessages(request, messages);
		}

		try {

			// 先取得符合條件的Event
			for (String oid : oids) {
				if (StringUtils.isNotBlank(oid)) {
					d = df.parse(oid.split("\\.")[0]);
					id = oid.split("\\.")[1];
					from = DateTime.parseDateTime(df1.format(d) + "T00:00:00");
					to = DateTime.parseDateTime(df1.format(d) + "T23:59:59");
					entries = am.findCalendarEventBy(
							IConstants.GOOGLE_SERVICES, from, to);
					for (CalendarEventEntry entry : entries) {
						if (id.equals(StringUtils.substringAfterLast(entry
								.getId(), "/"))) {
							delEntries.add(entry);
						}
					}
				}
			}

			if (!delEntries.isEmpty()) {

				// 開始批次刪除
				CalendarEventFeed batchRequest = new CalendarEventFeed();
				for (int i = 0; i < delEntries.size(); i++) {
					toDelEntry = delEntries.get(i);
					BatchUtils.setBatchId(toDelEntry, String.valueOf(i));
					BatchUtils.setBatchOperationType(toDelEntry,
							BatchOperationType.DELETE);
					batchRequest.getEntries().add(toDelEntry);
				}

				CalendarEventFeed feed = IConstants.GOOGLE_SERVICES.getFeed(
						Parameters.GOOGLE_EVENT_FEED_URL,
						CalendarEventFeed.class);
				Link batchLink = feed.getLink(Link.Rel.FEED_BATCH,
						Link.Type.ATOM);
				URL batchUrl = new URL(batchLink.getHref());
				CalendarEventFeed batchResponse = IConstants.GOOGLE_SERVICES
						.batch(batchUrl, batchRequest);
				boolean isSuccess = true;
				BatchStatus status = null;
				for (CalendarEventEntry entry : batchResponse.getEntries()) {
					// String batchId = BatchUtils.getBatchId(entry);
					if (!BatchUtils.isSuccess(entry)) {
						isSuccess = false;
						status = BatchUtils.getBatchStatus(entry);
						break;
					}
				}

				if (isSuccess) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Course.errorN1", "刪除成功"));
					saveMessages(request, messages);
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Course.errorN1", "刪除失敗："
									+ status.getReason()));
					saveErrors(request, messages);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "刪除失敗"));
			saveErrors(request, messages);
		}

		return unspecified(mapping, form, request, response);
	}

	public ActionForward chooseUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession(false);
		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		ActionMessages messages = new ActionMessages();
		String oid = Toolket.getSelectedIndexFromCookie(request,
				CALENDAR_CODE_INFO).replaceAll("\\|", "");

		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		// 先取得符合條件的Event
		Date d = df.parse(oid.split("\\.")[0]);
		String id = oid.split("\\.")[1];
		DateTime from = DateTime.parseDateTime(df1.format(d) + "T00:00:00");
		DateTime to = DateTime.parseDateTime(df1.format(d) + "T23:59:59");

		try {
			IConstants.GOOGLE_SERVICES.setUserCredentials(
					IConstants.GOOGLE_EMAIL_USERNAME,
					IConstants.GOOGLE_EMAIL_PASSWORD);
		} catch (AuthenticationException ae) {
			log.error(ae.getMessage(), ae);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "遠端認證資訊錯誤,請稍後再試,或電洽電算中心人員,謝謝!"));
			saveMessages(request, messages);
		}

		CalendarEventEntry[] entries = am.findCalendarEventBy(
				IConstants.GOOGLE_SERVICES, from, to);
		DynaBean bean = new LazyDynaBean();
		List<ExtendedProperty> props = null;

		for (CalendarEventEntry entry : entries) {
			if (id.equals(StringUtils.substringAfterLast(entry.getId(), "/"))) {

				bean.set("oid", entry.getTimes().get(0).getStartTime()
						.toUiString().split(" ")[0].replaceAll("-", "")
						+ "."
						+ StringUtils.substringAfterLast(entry.getId(), "/"));
				bean.set("title", entry.getTitle().getPlainText().trim());
				if (StringUtils.contains(entry.getPlainTextContent(), "主持人"))
					bean.set("content", StringUtils.abbreviate(StringUtils
							.substringAfter(entry.getPlainTextContent().trim(),
									"\n"), 20));
				else
					bean.set("content", StringUtils.abbreviate(entry
							.getPlainTextContent().trim(), 20));
				bean.set("location", StringUtils.defaultIfEmpty(entry
						.getLocations().get(0).getValueString(), ""));
				bean.set("startDate", Toolket.Date2Str(java.sql.Date
						.valueOf(entry.getTimes().get(0).getStartTime()
								.toUiString().split(" ")[0])));
				String[] startTime = (entry.getTimes().get(0).getStartTime()
						.toUiString().split(" ")[1]).split(":");
				String[] endTime = (entry.getTimes().get(0).getEndTime()
						.toUiString().split(" ")[1]).split(":");
				bean.set("startTime", startTime[0]);
				bean.set("startMin", startTime[1]);
				bean.set("range", String.valueOf(Integer.parseInt(endTime[0])
						- Integer.parseInt(startTime[0])));

				props = entry.getExtendedProperty();
				for (ExtendedProperty prop : props) {
					if ("host".equalsIgnoreCase(prop.getName()))
						bean.set("host", StringUtils.trimToEmpty(prop
								.getValue()));
				}
			}
		}

		session.setAttribute("calendarInfo", bean);

		setContentPage(session, "secretary/CalendarUpdate.jsp");
		return mapping.findForward(IConstants.ACTION_MAIN_NAME);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		AdminManager am = (AdminManager) getBean(IConstants.ADMIN_MANAGER_BEAN_NAME);
		DynaActionForm aForm = (DynaActionForm) form;
		String oid = aForm.getString("oid");
		ActionMessages messages = new ActionMessages();

		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		// 先取得符合條件的Event
		Date d = df.parse(oid.split("\\.")[0]);
		String id = oid.split("\\.")[1];
		DateTime from = DateTime.parseDateTime(df1.format(d) + "T00:00:00");
		DateTime to = DateTime.parseDateTime(df1.format(d) + "T23:59:59");

		try {
			IConstants.GOOGLE_SERVICES.setUserCredentials(
					IConstants.GOOGLE_EMAIL_USERNAME,
					IConstants.GOOGLE_EMAIL_PASSWORD);
		} catch (AuthenticationException ae) {
			log.error(ae.getMessage(), ae);
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"Course.errorN1", "遠端認證資訊錯誤,請稍後再試,或電洽電算中心人員,謝謝!"));
			saveMessages(request, messages);
		}

		// 修改流程:先刪除後新增
		CalendarEventEntry[] entries = am.findCalendarEventBy(
				IConstants.GOOGLE_SERVICES, from, to);
		for (CalendarEventEntry entry : entries) {
			if (id.equals(StringUtils.substringAfterLast(entry.getId(), "/"))) {

				CalendarEventFeed batchRequest = new CalendarEventFeed();
				BatchUtils.setBatchId(entry, "0");
				BatchUtils.setBatchOperationType(entry,
						BatchOperationType.DELETE);
				batchRequest.getEntries().add(entry);

				CalendarEventFeed feed = IConstants.GOOGLE_SERVICES.getFeed(
						Parameters.GOOGLE_EVENT_FEED_URL,
						CalendarEventFeed.class);
				Link batchLink = feed.getLink(Link.Rel.FEED_BATCH,
						Link.Type.ATOM);
				URL batchUrl = new URL(batchLink.getHref());
				CalendarEventFeed batchResponse = IConstants.GOOGLE_SERVICES
						.batch(batchUrl, batchRequest);
				boolean isSuccess = true;
				BatchStatus status = null;
				for (CalendarEventEntry e : batchResponse.getEntries()) {
					if (!BatchUtils.isSuccess(e)) {
						isSuccess = false;
						status = BatchUtils.getBatchStatus(entry);
						break;
					}
				}

				if (isSuccess) {

					String title = aForm.getString("title").trim();
					String content = aForm.getString("content").trim();
					String location = StringUtils.defaultIfEmpty(aForm
							.getString("location"), "");
					Date startDate = Toolket.parseNativeDate(request
							.getParameter("startDate"));
					String startHour = aForm.getString("startTime");
					String startMin = aForm.getString("startMin");
					String range = aForm.getString("range");
					String host = StringUtils.trimToNull(aForm.getString("host"));
					String recurData = "DTSTART;VALUE=DATE:20070501\r\n"
							+ "DTEND;VALUE=DATE:20070502\r\n"
							+ "RRULE:FREQ=WEEKLY;BYDAY=Tu;UNTIL=20070904\r\n";
					recurData = null;

					entry = new CalendarEventEntry();
					entry.setTitle(new PlainTextConstruct(title));
					StringBuilder builder = new StringBuilder();
					if (StringUtils.isNotBlank(host))
						builder.append("主持人:").append(host).append("\n");
					builder.append(content);
					entry
							.setContent(new PlainTextConstruct(builder
									.toString()));
					entry.setQuickAdd(false);
					// entry.setWebContent(wc);
					if (StringUtils.isNotBlank(location))
						entry.addLocation(new Where("", "", location));

					if (StringUtils.isNotBlank(host)) {
						ExtendedProperty property = new ExtendedProperty();
						property.setName("host");
						property.setValue(host);
						property.setRealm("CIS");
						entry.addExtendedProperty(property);
					}

					if (StringUtils.isNotBlank(recurData)) {
						Recurrence recur = new Recurrence();
						recur.setValue(recurData);
						entry.setRecurrence(recur);
					} else {
						Calendar cal = Calendar.getInstance();
						cal.setTime(startDate);
						cal.set(Calendar.HOUR_OF_DAY, Integer
								.parseInt(startHour));
						cal.set(Calendar.MINUTE, Integer.parseInt(startMin));
						cal.set(Calendar.SECOND, 0);
						cal.set(Calendar.MILLISECOND, 0);
						DateTime startTime = new DateTime(cal.getTime(),
								TimeZone.getDefault());

						cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(range));
						DateTime endTime = new DateTime(cal.getTime(), TimeZone
								.getDefault());
						When eventTimes = new When();
						eventTimes.setStartTime(startTime);
						eventTimes.setEndTime(endTime);
						entry.addTime(eventTimes);
					}

					IConstants.GOOGLE_SERVICES.insert(
							Parameters.GOOGLE_EVENT_FEED_URL, entry);
				} else {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("Course.errorN1", "更新時資料刪除失敗："
									+ status.getReason()));
					saveErrors(request, messages);
				}
			}
		}

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"Course.errorN1", "更新成功"));
		saveMessages(request, messages);
		return unspecified(mapping, form, request, response);
	}

	public ActionForward back(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return unspecified(mapping, form, request, response);
	}

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("course.onlineAddRemoveCourse.query", "query");
		map.put("fee.add.choose", "chooseAdd");
		map.put("fee.add.sure", "add");
		map.put("fee.update.choose", "chooseUpdate");
		map.put("fee.update.sure", "update");
		map.put("fee.delete", "delete");
		map.put("fee.back", "back");
		return map;
	}

}

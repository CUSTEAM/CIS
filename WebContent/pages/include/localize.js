function LocalizedYear(iYear, iFormat){
	var o;

	o=new Array(
	Array('', '', 0),
	Array('', '', 1911)
);
	return o[iFormat][0]+(iYear-o[iFormat][2])+o[iFormat][1];
}
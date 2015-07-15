var dialog = window.parent; // IE7 needs this
var oEditor = window.parent.InnerDialogLoaded();
var FCK = oEditor.FCK;
var FCKLang = oEditor.FCKLang;
var FCKConfig = oEditor.FCKConfig;
var FCKTools = oEditor.FCKTools;
var FCKBrowserInfo = oEditor.FCKBrowserInfo;


// default syntax object
function CodeSyntax() {
    var oCodeSyntax = new Object();
    oCodeSyntax.Code = oContainerPre.innerHTML;
    oCodeSyntax.Advanced = false;
    oCodeSyntax.Gutter = false;
    oCodeSyntax.NoControls = false;
    oCodeSyntax.Collapse = false;
    oCodeSyntax.Firstline = 0;
    oCodeSyntax.Showcolumns = false;

    return oCodeSyntax;
}

var oContainerPre = FCK.Selection.MoveToAncestorNode('PRE');
var oCodeSyntax = null;

// ----------------------
// populate our oCodeSyntax object
if (oContainerPre) {
    if (oContainerPre.tagName == 'PRE' && GetAttribute(oContainerPre, 'title') == 'code') {

        var CodeSettings = GetAttribute(oContainerPre, 'class', '');
        if (CodeSettings.length > 0) {

            // found valid code snippet, populate our CodeSyntax object
            oCodeSyntax = new CodeSyntax();

            if (CodeSettings.indexOf(";") > -1) {
                // advanced options set

                oCodeSyntax.Advanced = true;
                oCodeSyntax.Lang = CodeSettings.substring(CodeSettings.indexOf(":") + 1, CodeSettings.indexOf(";")).replace(/^\s+|\s+$/g, "");
                
                if (CodeSettings.indexOf("gutter") > -1)
                    oCodeSyntax.Gutter = true;

                if (CodeSettings.indexOf("toolbar") > -1)
                    oCodeSyntax.NoControls = true;

                if (CodeSettings.indexOf("collapse") > -1)
                    oCodeSyntax.Collapse = true;

                if (CodeSettings.indexOf("first-line") > -1) {

                    var match = /first-line: ([0-9]{1,4})/.exec(CodeSettings);
                    if (match != null && match.length > 0) {
                        oCodeSyntax.Firstline = match[1];
                    }
                    else {
                        oCodeSyntax.Firstline = 0;
                    }
                }


                if (CodeSettings.indexOf("ruler") > -1)
                    oCodeSyntax.Showcolumns = true;
            }
            else {
                oCodeSyntax.Lang = CodeSettings;
            }

        }

    } else {
        oContainerPre = null;
    }
}

// ----------------------
// config tabs
//window.parent.AddTab('TabSourceCode', FCKLang.SyntaxHightlightTab1);
//window.parent.AddTab('TabAdvanced', FCKLang.SyntaxHightlightTab2);
/*
function OnDialogTabChange(tabCode) {
    ShowE('divSourceCode', (tabCode == 'TabSourceCode'));
    ShowE('divAdvanced', (tabCode == 'TabAdvanced'));
}
*/
// ----------------------

window.onload = function() {

    // translate the dialog box texts
    oEditor.FCKLanguageManager.TranslatePage(document);
    // load current PRE block
    LoadSelected();
    // Show the "Ok" button.
    dialog.SetOkButton(true);
    // Select text field on load.
    SelectField('txtCode');
}

// ----------------------
// setup dialogue
function LoadSelected() {

    var ddLang = GetE('ddLang');

    if (!oCodeSyntax) {
        // creating new element
        if (FCKConfig.SyntaxHighlight2LangDefault != null) {

            for (count = 0; count < ddLang.length; count++) {

                if (ddLang.options[count].value == FCKConfig.SyntaxHighlight2LangDefault) {
                    ddLang.selectedIndex = count;
                    break;
                }
            }
        }

    }
    else {

        // editing existing element
        //document.getElementById('txtCode').value = HTMLDecode(oCodeSyntax.Code);
        document.getElementById('txtCode').value=oCodeSyntax.Code;
        ddLang.value = oCodeSyntax.Lang;

        // set any advanced options
        if (oCodeSyntax.Advanced) {
            if (oCodeSyntax.Gutter)
                GetE('chkGutter').checked = true;

            if (oCodeSyntax.NoControls)
                GetE('chkNoControls').checked = true;

            if (oCodeSyntax.Collapse)
                GetE('chkCollapse').checked = true;

            if (oCodeSyntax.Firstline > 0) {
                GetE('chkLineCount').checked = true;
                GetE('txtLineCount').disabled = false;
                GetE('txtLineCount').value = oCodeSyntax.Firstline

            }
            if (oCodeSyntax.Showcolumns)
                GetE('chkShowColumns').checked = true;

        }

    }
}

// ----------------------
// action on diagloue submit
function Ok() {
    var sCode = GetE('txtCode').value;
    var ddLang = GetE('ddLang').value
    var advanced = '';

    oEditor.FCKUndo.SaveUndoStep();
	
	
    if (!oContainerPre) {
        oContainerPre = FCK.CreateElement('xxx');
    }
    /*	
    if (GetE('chkGutter').checked)
        advanced += "; gutter: false";

    if (GetE('chkNoControls').checked)
        advanced += "; toolbar: false";

    if (GetE('chkCollapse').checked)
        advanced += "; collapse: true";

    if (GetE('chkLineCount').checked)
        advanced += "; first-line: " + GetE('txtLineCount').value;

    if (GetE('chkShowColumns').checked)
        advanced += "; ruler: true;";
	*/
    if (FCKBrowserInfo.IsIE) {
        // a bug in IE removes linebreaks in innerHTML, so lets use outerHTML instead
        oContainerPre.outerHTML = HTMLEncode(sCode);
    }
    
    else {
        oContainerPre.setAttribute("title", "code");
        oContainerPre.setAttribute("class", "brush: " + ddLang + advanced);
        //oContainerPre.innerHTML = HTMLEncode(sCode);
        oContainerPre.innerHTML =sCode;       
    }

    return true;
}

// ----------------------
// Helper functions
// ----------------------
function HTMLEncode(text) {
    if (!text)
        return '';
	/*
    text = text.replace(/&/g, '&amp;');
    text = text.replace(/</g, '&lt;');
    text = text.replace(/>/g, '&gt;');
	*/
    return text;
}

function HTMLDecode(text) {
    if (!text)
        return '';
	/*
    text = text.replace(/&gt;/g, '>');
    text = text.replace(/&lt;/g, '<');
    text = text.replace(/&amp;/g, '&');
    text = text.replace(/<br>/g, '\n');
    text = text.replace(/&quot;/g, '"');
	*/
    return text;
}

function changechk(checkbox, textfield) {

    if (checkbox.checked == true) {
        GetE(textfield).disabled = false;
    }
    else {
        GetE(textfield).disabled = true;
    }

}


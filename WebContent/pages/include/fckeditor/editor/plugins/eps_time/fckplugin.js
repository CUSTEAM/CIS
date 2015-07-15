FCKCommands.RegisterCommand('eps_time', new FCKDialogCommand('eps_time', FCKLang.insertClock, FCKPlugins.Items['eps_time'].Path + 'dialog/eps_time.html', 300, 400));

// Create the "SyntaxHighLight" toolbar button.
var oSyntaxhighlightItem = new FCKToolbarButton('eps_time', FCKLang.insertClock);
oSyntaxhighlightItem.IconPath = FCKPlugins.Items['eps_time'].Path + 'images/clock.gif';

FCKToolbarItems.RegisterItem('eps_time', oSyntaxhighlightItem);



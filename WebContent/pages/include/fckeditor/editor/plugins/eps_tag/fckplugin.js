FCKCommands.RegisterCommand('eps_tag', new FCKDialogCommand('eps_tag', FCKLang.SysMenu, FCKPlugins.Items['eps_tag'].Path + 'dialog/eps_tag.html', 300, 400));

// Create the "SyntaxHighLight" toolbar button.
var oSyntaxhighlightItem = new FCKToolbarButton('eps_tag', FCKLang.SysMenu);
oSyntaxhighlightItem.IconPath = FCKPlugins.Items['eps_tag'].Path + 'images/icon.gif';

FCKToolbarItems.RegisterItem('eps_tag', oSyntaxhighlightItem);



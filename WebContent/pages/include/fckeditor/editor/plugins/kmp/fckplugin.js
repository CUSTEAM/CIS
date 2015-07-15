FCKCommands.RegisterCommand('kmp', new FCKDialogCommand('kmp', FCKLang.kmp, FCKPlugins.Items['kmp'].Path + 'wpAudioPlay.html', 400, 400));

var KMPItem = new FCKToolbarButton('kmp', FCKLang.kmp);
KMPItem.IconPath = FCKPlugins.Items['kmp'].Path + 'kmp.gif';

FCKToolbarItems.RegisterItem('kmp', KMPItem);
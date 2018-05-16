# selenide-sample
参考

[Kotlin + SelenideでE2E自動テストのアプリケーションをつくってみた](http://blog.soushi.me/entry/2017/01/15/205751)

[Selenide入門](https://qiita.com/EichiSanden/items/ea857b46cbf5435b0c3b)

[PageObjectデザインパターンを利用して画面変更に強いUIテストを作成する](http://softwaretest.jp/labo/tech/labo-292/)

[PageObjectパターンではメソッドの返り値をどうするのが良いのか](https://yoshikiito.net/blog/archives/951)

[Pageオブジェクトパターン Selenideのお作法](https://qiita.com/tatesuke/items/0bac60172e7cfd12aeb1)

    <td width="300"> <img src="./1_files/pointeroff.gif" name="pointerI" width="8" height="13"> <input type="text" name="netId" maxlength="10" size="15" tabindex="1" value="" onkeyup="return false;" onblur="clearPointer('pointerI','LoginFormPcNetId.netId');" onfocus="showPointer('pointerI','LoginFormPcNetId.netId'); swKeyboardForInput('SwKeyboard1',['専用ID','専用パスワード'],0,true,true); return false;" style="width: 210px;" id="Number"></td> 
    <td width="300"> <img src="./1_files/pointeroff.gif" name="pointerI" width="8" height="13"> <input type="text" id="Number" name="netId" style="width: 210px;" tabindex="1" onkeyup="return false;" onfocus="showPointer('pointerI','LoginFormPcNetId.netId'); swKeyboardForInput('SwKeyboard1',['専用ID','専用パスワード'],0,true,true); return false;" onblur="clearPointer('pointerI','LoginFormPcNetId.netId');" value="" maxlength="10" size="15"></td> 
    <td width="300"> <img src="./1_files/pointeroff.gif" name="pointerP" width="8" height="13"> <input type="password" name="pssswrd" maxlength="20" size="25" tabindex="2" value="" onkeyup="return false;" onblur="clearPointer('pointerP','LoginFormPcNetId.pssswrd');" onfocus="showPointer('pointerP','LoginFormPcNetId.pssswrd'); swKeyboardForInput('SwKeyboard1',['専用ID','専用パスワード'],1,true,true); return false;" style="width:210px;" id="Password"> 
    <td width="300"> <img src="./1_files/pointeroff.gif" name="pointerP" width="8" height="13"> <input id="Password" name="pssswrd" style="width:210px;" tabindex="2" onkeyup="return false;" onfocus="showPointer('pointerP','LoginFormPcNetId.pssswrd'); swKeyboardForInput('SwKeyboard1',['専用ID','専用パスワード'],1,true,true); return false;" onblur="clearPointer('pointerP','LoginFormPcNetId.pssswrd');" type="password" value="" size="25" maxlength="20"> 

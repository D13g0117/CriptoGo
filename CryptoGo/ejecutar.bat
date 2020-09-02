@echo off
set extra=
FOR /L %%i IN (200,1,290) DO (
	set extra=!extra!;C:\Java\jdk1.8.0_%%i\bin;C:\Program Files\Java\jdk1.8.0_%%i\bin
)

set classpath=ScwJadex.jar;^
src\lib\Base64.jar;^
src\lib\GraphLayout.jar;^
src\lib\bcel.jar;^
src\lib\http.jar;^
src\lib\iiop.jar;^
src\lib\jade.jar;^
src\lib\jadeTools.jar;^
src\lib\jadex_examples.jar;^
src\lib\jadex_jadeadapter.jar;^
src\lib\jadex_rt.jar;^
src\lib\jadex_standalone.jar;^
src\lib\jadex_tools.jar;^
src\lib\jhall.jar;^
src\lib\jibx-bind.jar;^
src\lib\jibx-extras.jar;^
src\lib\jibx-run.jar;^
src\lib\xpp3.jar;^
src;^
src\lib\commons-commons\commons-codec-1.3.jar

FOR /L %%A IN (1,1,1) DO (
    java jadex.adapter.standalone.Platform "mesa:jar:file:/C:\Users\diego\OneDrive\TFG\Proyecto\CriptoGo\CryptoGo\CryptoGoJadex!/src/mesa/mesa.agent.xml(default)" "jugador1:jar:file:/C:\Users\diego\OneDrive\TFG\Proyecto\CriptoGo\CryptoGo\CryptoGoJadex!/src/jugador/jugador.agent.xml(default)" "jugador2:jar:file:/C:\Users\diego\OneDrive\TFG\Proyecto\CriptoGo\CryptoGo\CryptoGoJadex!/src/jugador/jugador.agent.xml(default)" "jugador3:jar:file:/C:\Users\diego\OneDrive\TFG\Proyecto\CriptoGo\CryptoGo\CryptoGoJadex!/src/jugador/jugador.agent.xml(default)" "jugador4:jar:file:/C:\Users\diego\OneDrive\TFG\Proyecto\CriptoGo\CryptoGo\CryptoGoJadex!/src/jugador/jugador.agent.xml(default)" "jugador5:jar:file:/C:\Users\diego\OneDrive\TFG\Proyecto\CriptoGo\CryptoGo\CryptoGoJadex!/src/jugador/jugador.agent.xml(default)" "jugador6:jar:file:/C:\Users\diego\OneDrive\TFG\Proyecto\CriptoGo\CryptoGo\CryptoGoJadex!/src/jugador/jugador.agent.xml(default)" "jugador7:jar:file:/C:\Users\diego\OneDrive\TFG\Proyecto\CriptoGo\CryptoGo\CryptoGoJadex!/src/jugador/jugador.agent.xml(default)" "jugador8:jar:file:/C:\Users\diego\OneDrive\TFG\Proyecto\CriptoGo\CryptoGo\CryptoGoJadex!/src/jugador/jugador.agent.xml(default)" -nogui
)

pause 


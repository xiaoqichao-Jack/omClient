@echo off
>nul 2>&1 "%SYSTEMROOT%\system32\cacls.exe" "%SYSTEMROOT%\system32\config\system"
if '%errorlevel%' NEQ '0' (
goto UACPrompt
) else ( goto gotAdmin )
:UACPrompt
echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
echo UAC.ShellExecute "%~s0", "", "", "runas", 1 >> "%temp%\getadmin.vbs"
"%temp%\getadmin.vbs"
exit /B
:gotAdmin
if exist "%temp%\getadmin.vbs" ( del "%temp%\getadmin.vbs" )
color 0A
echo stop the zabbix_agentd service...
if %processor_architecture% EQU x86 C:\app\zabbix\bin\win64\zabbix_agentd.exe -c C:\app\zabbix\conf\zabbix_agentd.win.conf -x
if %processor_architecture% EQU AMD64 C:\app\zabbix\bin\win64\zabbix_agentd.exe -c C:\app\zabbix\conf\zabbix_agentd.win.conf -x
echo uninsatll client...
if %processor_architecture% EQU x86 C:\app\zabbix\bin\win64\zabbix_agentd.exe -c C:\app\zabbix\conf\zabbix_agentd.win.conf -d
if %processor_architecture% EQU AMD64 C:\app\zabbix\bin\win64\zabbix_agentd.exe -c C:\app\zabbix\conf\zabbix_agentd.win.conf -d
echo Removing the firewall allows access to the 10050 port TCP rule...
netsh advfirewall firewall delete rule name="allow_tcp_10050"
echo finish
@echo off
%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit cd /d "%~dp0"
color 0A
echo "startup client software..."
if %processor_architecture% EQU x86 C:\app\zabbix\bin\win32\zabbix_agentd.exe -s -c C:\app\zabbix\conf\zabbix_agentd.win.conf 
if %processor_architecture% EQU AMD64 C:\app\zabbix\bin\win64\zabbix_agentd.exe -s -c C:\app\zabbix\conf\zabbix_agentd.win.conf 
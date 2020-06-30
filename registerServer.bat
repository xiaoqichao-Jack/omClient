@echo off
%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit cd /d "%~dp0"
color 0A
echo "Switch directories c:/client-1.0.........."
cd C:/client-1.0
echo "Service registration....."
omClient-1.0.exe install
echo "start servers....."
net start omClient-1.0
pause

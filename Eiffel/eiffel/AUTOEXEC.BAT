@ECHO OFF
SET winbootdir=C:\WIN95
SET HOME=SYS:USERS\WIN95
SET PATH=Z:\PUBLIC;C:\WIN95;C:\WIN95\COMMAND;C:\DOS;C:\JAZZ;C:\LOADLIN;C:\NWCLIENT;C:\TCPLUS\BIN;c:\es13s\bin;c:\djgcc\bin;
SET eiffel_s=c:\es13s
SET PROMPT=[$P$G]
 set COMPILER_PATH=c:/djgcc/bin
   set C_INCLUDE_PATH=c:/djgcc/include
   set LIBRARY_PATH=c:/djgcc/lib
   set TMPDIR=c:/temp
   set GO32=emu c:\djgcc\emu387\emu387
lh c:\dos\mouse
doskey

REM
REM The following lines have been created by Windows. Do not modify them.
REM
C:
CD C:\ES13S
CALL COMMAND.COM
C:\WIN95\WIN.COM /WX

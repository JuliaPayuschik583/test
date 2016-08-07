@echo off
"C:\Program Files\PostgreSQL\9.4\bin\psql.exe" -h localhost -p 5433 -U postgres -d postgres -f run_main.sql
pause
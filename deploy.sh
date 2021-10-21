echo 'Termitate server'
kill -9 `cat current_pid.txt`
rm current_pid.txt

echo 'Start server'
nohup ./mvnw spring-boot:run
echo $! > current_pid.txt

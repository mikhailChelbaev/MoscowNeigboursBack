echo 'Termitate server'
kill -9 `cat current_pid.txt`
rm current_pid.txt

echo 'Start server'
nohup java -jar data/product.jar > log.txt &
echo $! > current_pid.txt

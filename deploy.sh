echo 'Termitate server'
Kill -9 `cat current_pid.txt`
rm current_pid.txt

echo 'Start server'
nohup java -jar data/product.jar > log.txt 2>&1 &
echo $! > current_pid.txt

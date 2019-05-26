 
# Cum se instaleaza docker:

sudo apt-get install docker.io

# Trebuie sa rulam urmatoarele comenzi pentru a putea sa pornim 
# containere fara drept de root:

sudo groupadd docker
sudo gpasswd -a $USER docker
newgrp docker

# se da restart la PC

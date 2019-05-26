cd $1
rm -r Docker/sandbox
output="$(docker images -a | grep compile-run | wc -l)"
docker build -t compile-run Docker
docker rm ip-container
docker run --name=ip-container compile-run
docker cp ip-container:/sandbox Docker

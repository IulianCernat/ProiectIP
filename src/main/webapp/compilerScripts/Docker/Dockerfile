FROM gcc:4.9
RUN mkdir sandbox
COPY main.cpp suma.in Dockerfile input.txt /sandbox/
WORKDIR /sandbox
RUN g++ main.cpp
RUN ./a.out < input.txt > output.txt


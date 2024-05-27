FROM python:alpine3.11

COPY requirements.txt .

RUN pip3 install -r ./requirements.txt

COPY ./src /app/

WORKDIR /app

CMD ["python", "main.py"]
FROM python:3.11-alpine3.19

WORKDIR /app

COPY requirements.txt .

RUN pip install -r requirements.txt

COPY src/ .


CMD ["python", "main.py"]

FROM evau23/openai-gym
RUN pip install flask
EXPOSE 5000
COPY gym_http_server.py /
CMD ["python", "/gym_http_server.py"]

from locust import HttpLocust, TaskSet, task
import json

class WebsiteTasks(TaskSet):
    def on_start(self):
        headers = {'content-type': 'application/json'}
        for id in range(0,40):
            pauta_id = str(id)
            payload = {
                "pauta": { "id": pauta_id }
            }
            self.client.post("/v1/sessoes", data=json.dumps(payload), headers=headers)

    @task
    def read(self):
        for id in range(0,40):
            sessao_id = str(id)
            self.client.get("/v1/sessoes/" + sessao_id)

class WebsiteUser(HttpLocust):
    task_set = WebsiteTasks
    min_wait = 50000
    max_wait = 50000
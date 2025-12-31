import requests
from flask import Flask, jsonify, Blueprint
from flask_restful import Api, Resource
from flasgger import Swagger

app = Flask(__name__)
Swagger(app)
atm_finance_bp = Blueprint('finance', __name__)

def get_exchange_rates():
    url = "https://api.exchangerate-api.com/v4/latest/USD"
    response = requests.get(url)
    return response.json() if response.status_code == 200 else None

class ExchangeResource(Resource):
    def get(self, currency):
        """
        Doviz kuru sorgulama ucu.
        ---
        parameters:
          - name: currency
            in: path
            type: string
            required: true
        responses:
          200:
            description: Basarili
        """
        data = get_exchange_rates()
        if data and currency.upper() in data['rates']:
            rate = data['rates'][currency.upper()]
            return jsonify({"base": "USD", "target": currency.upper(), "rate": rate})
        return {"message": "Kur bulunamadi"}, 404

api = Api(atm_finance_bp)
api.add_resource(ExchangeResource, '/exchange/<string:currency>')
app.register_blueprint(atm_finance_bp, url_prefix='/api')

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
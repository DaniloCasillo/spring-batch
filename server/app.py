from flask import Flask

app = Flask(__name__)


@app.route('/person')
def get_persons():  # put application's code here
    response = [{
        "name":"danilo",
        "surname":"casillo"
    },
        {
            "name":"vincenzo",
            "surname":"racca"
        },
        {
            "name":"antonio",
            "surname":"di girolamo"
        }]
    return response

@app.route('/car')
def get_cars():  # put application's code here
    response = [{
        "make":"fiat",
        "model":"punto",
        "color":"black"
    },
        {
            "make":"opel",
            "model":"agila",
            "color":"gray"
        },
        {
            "make":"alfa romeo",
            "model":"stelvio",
            "color":"red"
        }]
    return response


if __name__ == '__main__':
    app.run()

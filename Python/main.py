import pymysql
from app import app
from db_config import mysql
from flask import jsonify
from flask import flash, request
		
@app.route('/add', methods=['POST'])
def add_user():
	_name = request.args.get('name')
	_correo = request.args.get('correo')
	_pass = request.args.get('pass')
	sql = "INSERT INTO USUARIO VALUE(NULL,%s, %s, %s, 'API_PYTHON')"
	data = (_name, _correo, _pass)
	conn = mysql.connect()
	cursor = conn.cursor()
	cursor.execute(sql, data)
	conn.commit()
	resp = jsonify('USUARIO REGISTRADO CON PYTHON')
	resp.status_code = 200
	cursor.close() 
	conn.close()
	print('USUARIO REGISTRADO CON PYTHON')
	return resp
		
@app.route('/users', methods=['GET'])
def users():
	try:
		conn = mysql.connect()
		cursor = conn.cursor(pymysql.cursors.DictCursor)
		cursor.execute("SELECT * FROM USUARIO")
		rows = cursor.fetchall()
		resp = jsonify(rows)
		resp.status_code = 200
		return resp
	except Exception as e:
		print(e)
	finally:
		print("TODOS LOS USUARIOS CON PYTHON")
		cursor.close() 
		conn.close()
		
@app.route('/userpython', methods=['GET'])
def user():
	try:
		conn = mysql.connect()
		cursor = conn.cursor(pymysql.cursors.DictCursor)
		cursor.execute("SELECT * FROM USUARIO WHERE ORIGEN='API_PYTHON'")
		row = cursor.fetchall()
		resp = jsonify(row)
		resp.status_code = 200
		return resp
	except Exception as e:
		print(e)
	finally:
		print(row)
		print('USUARIOS DE PYTHON')
		cursor.close() 
		conn.close()

@app.route('/update', methods=['PUT'])
def update_user():
	_id = request.args.get('id')
	_name = request.args.get('name')
	_correo = request.args.get('correo')
	_pass = request.args.get('pass')
	sql = "UPDATE USUARIO SET NAME=%s, CORREO=%s, PASS=%s, ORIGEN='PYTHON_API' WHERE IDUSUARIO=%s"
	data = (_name, _correo, _pass, _id)
	conn = mysql.connect()
	cursor = conn.cursor()
	cursor.execute(sql, data)
	conn.commit()
	resp = jsonify('USUARIO ACTUALIZADO CON PYTHON')
	resp.status_code = 200
	cursor.close() 
	conn.close()
	return resp
		
@app.route('/delete', methods=['DELETE'])
def delete_user():
	try:
		_id = request.args.get('id')
		conn = mysql.connect()
		cursor = conn.cursor()
		cursor.execute("DELETE FROM USUARIO WHERE IDUSUARIO=%s", (_id,))
		conn.commit()
		resp = jsonify('User deleted successfully!')
		resp.status_code = 200
		return resp
	except Exception as e:
		print(e)
	finally:
		print("USUARIO ELIMINADO CON PYTHON")
		cursor.close() 
		conn.close()
		
@app.errorhandler(404)
def not_found(error=None):
    message = {
        'status': 404,
        'message': 'Not Found: ' + request.url,
    }
    resp = jsonify(message)
    resp.status_code = 404

    return resp
		
if __name__ == "__main__":
    app.run(host= '192.168.43.189', port=5000, debug=True)
# Review-Restaurant-Stantee-TU

## เริ่มต้นโปรเจค
สร้างไฟล์ .env ขึ้นมาใน directory และกำหนดค่าต่างๆตาม .env.example โดยค่าต่างๆสามารถตั้งได้ตามสะดวกเลย ไม่มีผลต่อการเชื่อมต่อ

```.env
DB_USER=<username สำหรับเชื่อม database>
DB_PASSWORD=<password สำหรับเชื่อม database>
DB_NAME=app_db

PGADMIN_EMAIL=<email สำหรับตัว database management tool>
PGADMIN_PASSWORD=<password สำหรับตัว database management tool>
```

## การเปิดใช้งาน Database
> [!IMPORTANT]  
> คนที่ทำ Frontend, Backend, Database Engineer ควรเปิดใช้งาน database ระหว่างพัฒนาเผื่อการเรียก API และให้ข้อมูล Sync กัน

หลังจากที่ clone โปรเจคมาแล้วให้เปิด Git Bash ขึ้นมาใน Terminal ของ IDE และรันคำสั่ง

```bash
$ ./script.sh
```
terminal จะขึ้น "Deployment Complete" เมื่อเราเปิดใช้งานสำเร็จพร้อมแสดง "HOST" 
```bash
==========================================
[+] Running 4/4
 ✔ Network review-restaurant-stantee-tu_default         Created                                                                                                                               0.0s 
 ✔ Volume "review-restaurant-stantee-tu_postgres_data"  Created                                                                                                                               0.0s 
 ✔ Container postgres_db                                Started                                                                                                                               0.6s 
 ✔ Container pgadmin_container                          Started                                                                                                                               0.8s 
Docker containers are up and running.

==========================================
Deployment Complete!
==========================================
HOST is running at: x.x.x.x
Press enter to exit...
```

## การใช้งาน Database Management Tool (Database Engineer)

1. สำหรับคนไม่ได้โหลด database management tool ไว้บนเครื่องสามารถเปิด browser และเข้าไปที่ http://localhost:5050/ </br>
2. ในหน้า login ให้นำ PGADMIN_EMAIL และ PGADMIN_PASSWORD ไปใส่ในฟอร์ม login </br></br>
<img width="1920" height="903" alt="image" src="https://github.com/user-attachments/assets/1dfedfeb-0f5c-4bb0-a8f0-7c3f2cb420a3" /></br></br>
3. กดที่ Add New Server </br></br>
<img width="698" height="549" alt="image" src="https://github.com/user-attachments/assets/29baa63f-48b2-45d2-9fa5-8a69e96256fa" /></br></br>
4. Name(ชื่อ server) ใส่ชื่ออะไรก็ได้ </br></br>
<img width="696" height="543" alt="image" src="https://github.com/user-attachments/assets/04ab9f5c-3753-4308-929e-b5c1a3c108d0" /></br></br>
5. Host name/address ใส่ชื่อ HOST ที่อยู่ในรูปแบบ IPv4 ที่ได้หลังจากรัน script.sh ไป
6. username, password ใส่ค่า DB_USER, DB_PASSWORD ที่กำหนดไว้ใน .env
7. เมื่อกด Save แล้วสามารถเริ่ม สร้าง, แก้ไขตารางได้ที่ app_db > Schemas > Tables






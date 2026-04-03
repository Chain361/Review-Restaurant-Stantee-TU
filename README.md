# Review-Restaurant-Stantee-TU

## เริ่มต้นโปรเจค

สร้างไฟล์ .env ขึ้นมาใน directory และกำหนดค่าต่างๆตาม .env.example โดยค่าต่างๆสามารถตั้งได้ตามสะดวกเลย ไม่มีผลต่อการเชื่อมต่อ

### (อัพเดต phrase2)

```.env
DB_USER=<username สำหรับเชื่อม database>
DB_PASSWORD=<password สำหรับเชื่อม database>
DB_NAME=app_db
DB_PORT=
**DB_PORT ปกติเป็น 5432 ถ้ารัน springboot ไม่ได้ใช้ 5433

PGADMIN_EMAIL=<email สำหรับตัว database management tool>
PGADMIN_PASSWORD=<password สำหรับตัว database management tool>
```

### [คู่มือ Database (ทำทุกคน)](./guides/DB_Guide.md)

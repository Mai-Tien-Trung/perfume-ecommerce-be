# Tên Dự Án (Ví dụ: Perfume Store E-Commerce)

## 🌟 Giới thiệu
Website bán nước hoa với đầy đủ chức năng từ đăng nhập, quản lý sản phẩm, giỏ hàng đến thanh toán.

## 🛠️ Công nghệ sử dụng
- Backend: Java Spring Boot
- Frontend: ReactJS / Vite / TailwindCSS
- Cơ sở dữ liệu: PostgreSQL
- Thanh toán: VNPay / PayOS (nếu có)
- Email: JavaMail

##  Tính năng chính
### Người dùng (CUSTOMER):
- Đăng ký / đăng nhập
- Xem sản phẩm, tìm kiếm, lọc
- Thêm vào giỏ hàng, đặt hàng
- Lịch sử đơn hàng, đánh giá sản phẩm

### Quản lý (MANAGER):
- CRUD sản phẩm
- Xem và xử lý đơn hàng
- Xem đánh giá

### Quản trị viên (ADMIN):
- Tất cả quyền của Manager +
- Quản lý user (phân quyền, xóa,...)
- Thống kê dashboard
- Quản lý danh mục, thương hiệu

### Backend
```bash
cd backend
./mvnw spring-boot:run

Create database AppFood;
use AppFood;

Create table SystemMasterData(
	Id int primary key auto_increment,
    `Code` varchar(100),
    `Name` nvarchar(250),
    ParentId int,
    CreatedDate datetime default now(),
    CreatedBy int,
    UpdatedDate datetime default now(),
    UpdatedBy int,
    IsDeleted bool
);

Create table Admins(
	Id int primary key auto_increment,
    FullName nvarchar(250),
    HashPassword nvarchar(512),
    Identity nvarchar(250),
    Phone nvarchar(250),
    Birthday datetime,
    Address nvarchar(250),
    LoginDate datetime,
    ForgotCode nvarchar(100),
    ExpriedForgot datetime (100),
    RefreshToken nvarchar(500),
    ExpriedToken datetime,
    CreatedDate datetime default now(),
    CreatedBy int,
    UpdatedDate datetime default now(),
    UpdatedBy int,
    IsDeleted bool
);

Create table Shops(
	Id int primary key auto_increment,
    FullName nvarchar(250),
    HashPassword nvarchar(512),
    Avatar nvarchar(500),
    Identity nvarchar(250),
    Birthday datetime,
    Phone nvarchar(250),
    Email nvarchar(250),
    Address nvarchar(250),
    Rate int,
    OpenTime date,
    CloseTime date,
    IsActive bool,
    LoginDate datetime,
    ForgotCode nvarchar(100),
    ExpriedForgot datetime (100),
    RefreshToken nvarchar(500),
    ExpriedToken datetime,
    CreatedDate datetime default now(),
    CreatedBy int,
    UpdatedDate datetime default now(),
    UpdatedBy int,
    IsDeleted bool
);

create table Category(
	Id int primary key auto_increment,
	Code nvarchar(100),
    Name nvarchar(500)
);

Create table CategoryShop(
	Id int primary key auto_increment,
	Name nvarchar(500),
    CreatedByShop int
);

Create table SaleCode(
	Id int primary key auto_increment,
	Name nvarchar(500),
    Code nvarchar(250),
    Value int,
    IsPercent bool,
    MinBill int,
    ExpriedDate date,
    Stock int,
    CreatedByShop int
);

-- đồ ăn trong quán
Create table Product(
	Id int primary key auto_increment,
	Name nvarchar(500),
    Image nvarchar(500),
    Description nvarchar(500),
    IsMain bool,
    Rate int,
    CreatedByShop int
);


Create table Size(
	Id int primary key auto_increment,
	Name nvarchar(500),
    CreatedByShop int
);

Create table ProductSize(
	Id int primary key auto_increment,
    ProductId int,
    SizeId int,
    Price decimal(9,0),
    StockInDay int
);

Create table ProductCategory(
	Id int primary key auto_increment,
    ProductId int,
    CategoryId int,
    CategoryInShopId int
);

Create table ProductOption(
	Id int primary key auto_increment,
    MainProductId int,
	SubProduct int
);

create table Customer(
	Id int primary key auto_increment,
    FullName nvarchar(500),
    HashPassword nvarchar(512),
    Phone nvarchar(500),
    Email nvarchar(500),
    Address nvarchar(500)
);

Create table Orders(
	Id int primary key auto_increment,
    CustomerId int,
    ShopId int,
    Address nvarchar(500),
    SaleCode int,
    ShipPrice decimal(9,0),
    TotalMoney decimal(9,0),
    CreatedDate datetime,
    `Status` int
);

create table OrderDetails(
	Id int primary key auto_increment,
    OrderId int,
    CustomerId int,
    ProductSizeId int,
    Stock int,
    Price decimal(9,0),
    Note nvarchar(500)
);


Create table ProductRate(
	Id int primary key auto_increment,
    ProductId int,
    CustomerId int,
    Rate int,
    `Comment` nvarchar(500),
    ParentId int
);
# 📊 فاز سوم: ساختار تیم تولید پادکست (Team Tree)

## 🎯 هدف فاز

در این فاز ما یک **درخت سلسله‌مراتبی** را برای نمایش ساختار تیم تولید هر پادکست پیاده‌سازی می‌کنیم.

**ویژگی‌های اصلی:**

- هر عضو تیم می‌تواند **چند زیردست** داشته باشد
- اضافه کردن، حذف، و جستجوی اعضای تیم
- نمایش خوانا و مرتب‌شده‌ی ساختار تیم
- دو نوع جستجو: **BFS** و **DFS**

**کاربرد:**
مدیران پلتفرم می‌توانند ساختار تیم هر پادکست را مدیریت کنند و مسیر ارتباط بین اعضا را مشاهده کنند.

---

## 📁 فایل‌ها و نقش آن‌ها

### 1️⃣ **TeamMember.java** (نود)

تنها **نگهداری داده** می‌کند:

```java
String name;              // نام عضو
TeamMember firstChild;    // اولین زیردست
TeamMember nextSibling;   // عضو هم‌سطح بعدی
```

**ساختار First Child / Next Sibling:**

- هر عضو یک `firstChild` دارد که به اولین زیردست اشاره می‌کند
- تمام برادران (sibling) به هم زنجیر شده‌اند با `nextSibling`
- این روش از استفاده آرایه یا لیست جلوگیری می‌کند

**مثال:**

```text
       Producer
       /  |  \
    Editor Host SoundEngineer
    /              / \
ScriptWriter   Intern1 Intern2
```


درون حافظه:

```text
Producer:
  firstChild → Editor → nextSibling → Host → nextSibling → SoundEngineer
    Editor:
      firstChild → ScriptWriter
    SoundEngineer:
      firstChild → Intern1 → nextSibling → Intern2
```

---

### 2️⃣ **TeamTree.java** (منطق اصلی)

#### **insert(String parentName, String childName)**

- **حالت 1:** `childName == null` → اضافه کردن ریشه (اگر درخت خالی باشد)
- **حالت 2:** یافتن `parentName` با `searchDFSNode()` → افزودن `childName` به انتهای فرزندان والد
- **خطا:** اگر والد وجود نداشته باشد → `"Parent <name> does not exist"`

**کد اصلی:**

```java
TeamMember parent = searchDFSNode(root, parentName);
if (parent == null) {
    System.out.println("Parent " + parentName + " does not exist");
    return;
}
// اضافه کردن به انتهای برادران
```

#### **display()**

نمایش هر عضو و فرزندان مستقیم آن:

```text
Parent: Child1,Child2,Child3
```

سپس **بازگشتی** برای تمام زیردرخت‌ها.

#### **searchDFS(String name)**

**جستجوی عمق‌اول (Depth-First Search):**

- از ریشه شروع
- هر یک از فرزندان را بررسی کنید
- اگر یافت نشد، به `firstChild` برروید
- اگر یافت شد → بازگشت **مسیر کامل** از ریشه تا هدف

**خروجی:** `"Path (DFS): Root -> ... -> Target"`

#### **searchBFS(String name)**

**جستجوی عرض‌اول (Breadth-First Search):**

- هر سطح را یکی یکی بررسی کنید
- اگر یافت نشد، به سطح بعد برروید

**خروجی:** `"Path (BFS): Root -> ... -> Target"`

#### **delete(String name)**

حذف عضو و **تمام زیردرخت** آن:

- اگر مورد نظر **ریشه** باشد → درخت خالی شود
- در غیر این صورت → یافتن والد و قطع زنجیر

**خروجی:** `"Node <name> and its subtree deleted"`

---

## 🔄 مثال جزء‌به‌جزء

### ورودی

```text
insert Producer              → Producer ریشه
insert Producer Editor       → Editor زیردست Producer
insert Producer Host         → Host زیردست Producer
insert Producer SoundEngineer → SoundEngineer زیردست Producer
insert Editor ScriptWriter   → ScriptWriter زیردست Editor
display
search DFS ScriptWriter
delete SoundEngineer
display
```

### خروجی

```text
Producer: Editor,Host,SoundEngineer
Editor: ScriptWriter
Host:
SoundEngineer:
ScriptWriter:

Path (DFS): Producer -> Editor -> ScriptWriter

Node SoundEngineer and its subtree deleted

Producer: Editor,Host
Editor: ScriptWriter
Host:
ScriptWriter:
```

### توضیح

1. **ریشه Producer** ایجاد شود
2. **3 عضو** مستقیم زیر Producer اضافه شوند
3. **ScriptWriter** زیر Editor اضافه شود
4. **نمایش:** هر عضو و فرزندان مستقیمش چاپ شود
5. **جستجوی DFS:** مسیر Producer → Editor → ScriptWriter نمایش داده شود
6. **حذف SoundEngineer:** عضو و تمام فرزندانش حذف شوند
7. **نمایش دوبارە:** SoundEngineer دیگر وجود نداشته باشد

---

## 📋 ورودی/خروجی فرمت

### دستورات

```bash
insert <name>                 # ایجاد ریشه (اگر درخت خالی باشد)
insert <parent> <child>       # اضافه کردن child به عنوان زیردست parent
display                       # نمایش کل درخت
search DFS <name>            # جستجوی DFS
search BFS <name>            # جستجوی BFS
delete <name>                # حذف node و تمام زیردرخت
```

### نمونه خروجی

```text
Producer: Editor,Host,SoundEngineer
Editor: ScriptWriter
Host:
SoundEngineer: Intern1,Intern2

Path (DFS): Producer -> Editor -> ScriptWriter
Path (BFS): Producer -> SoundEngineer -> Intern2
Node Manager NOT found
Node SoundEngineer and its subtree deleted
```

---

## ⚡ نکات و محدودیت‌ها

| نکته | توضیح |
| ------ | -------- |
| **نام‌های یکتا** | هر عضو یک نام منحصربه‌فرد دارد |
| **بدون تکرار** | یک فرد نمی‌تواند دوبار اضافه شود |
| **حذف کامل** | حذف یک عضو باعث حذف تمام زیردرختش می‌شود |
| **جستجوی ریشه** | اگر صرفاً یک نام داده شود و درخت خالی باشد، آن عضو ریشه می‌شود |

---

## 🚀 نحوه اجرا

### 1️⃣ ورود به پوشه

```bash
cd src/phase3_team
```

### 2️⃣ کامپایل

```bash
javac TeamMember.java TeamTree.java Phase3Main.java
```

### 3️⃣ اجرای تعاملی

```bash
java Phase3Main
```

سپس دستورات را یکی یکی وارد کنید و نتایج را مشاهده کنید.

---

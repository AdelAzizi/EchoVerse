# فاز سوم: ساختار تیم تولید پادکست (Team Tree)

## 📌 معرفی فاز سوم

**مسئله:**
پلتفرم EchoVerse می‌خواهد ساختار سلسله‌مراتبی تیم تولید هر پادکست را مدیریت کند. هر پادکست یک تیم دارد که شامل تهیه‌کننده، میزبان، مهندس صوت و دستیاران است. نیاز است بتواند اعضا را اضافه کند، حذف کند و مسیرهای ارتباط بین اعضا را جستجو کند.

**راه‌حل:**
پیاده‌سازی یک **درخت عمومی (General Tree)** با نمایش **First-Child/Next-Sibling** که به دو روش **BFS** و **DFS** جستجو‌پذیر باشد.

**چرا First-Child/Next-Sibling؟**
- هر عضو می‌تواند چند زیردست داشته باشد (بدون محدودیت)
- فضای حافظه کم (فقط دو اشاره‌گر)
- نمایش طبیعی درخت‌های عمومی

**قانون مهم:**
بدون `ArrayList`، `LinkedList`، یا ساختارهای جاهز. تمام درخت دستی پیاده‌سازی می‌شود.

---

## 🎯 اهداف و الزامات

### اهداف فاز سوم

1. **ساخت درخت عمومی:** نمایش سلسله‌مراتب تیم با First-Child/Next-Sibling
2. **درج اعضا:** اضافه کردن عضو جدید تحت یک والد معین
3. **حذف اعضا:** حذف عضو و تمام زیردستان
4. **نمایش:** چاپ ساختار تیم به صورت خوانا
5. **جستجوی DFS:** جستجوی عمق‌اول برای یافتن عضو و مسیر
6. **جستجوی BFS:** جستجوی عرض‌اول برای یافتن عضو و مسیر

### الزامات

- بدون کتابخانه‌های آماده برای درخت
- First-Child/Next-Sibling درست پیاده‌سازی شود
- هر دو الگوریتم BFS و DFS کامل‌اً کار کنند
- مسیرهای صحیح نمایش داده شوند

---

## 📁 ساختار فایل‌ها

فایل‌های فاز سوم در پوشه `src/phase3_team/` قرار دارند:

```text
src/phase3_team/
    ├── TeamMember.java      (کلاس نود)
    ├── TeamTree.java        (کلاس درخت)
    ├── Phase3Main.java      (برنامه اصلی)
    └── phase3.md            (این فایل)
```

---

## 💻 شرح فایل‌ها همراه با توضیح کد

### 1️⃣ TeamMember.java - ساختار نود

**نقش:** نمایندگی هر عضو تیم در درخت.

**کد کامل:**

```java
public class TeamMember {
    String name;                 // نام عضو
    TeamMember firstChild;       // اولین زیردست
    TeamMember nextSibling;      // عضو هم‌سطح بعدی
    
    public TeamMember(String name) {
        this.name = name;
        this.firstChild = null;
        this.nextSibling = null;
    }
}
```

**توضیح خط به خط:**
- `String name`: نام عضو تیم (مثلاً "Producer"، "Editor")
- `TeamMember firstChild`: اشاره‌گر به اولین زیردست (فرزند)
- `TeamMember nextSibling`: اشاره‌گر به عضو هم‌سطح بعدی (برادر)
- سازنده: نام را تنظیم کنید، اشاره‌گرها null

**نحوهٔ کار ساختار:**
```
Producer (root):
  firstChild → Editor → nextSibling → Host → nextSibling → SoundEngineer → null
    Editor.firstChild → ScriptWriter → nextSibling → null
    Host.firstChild → null
    SoundEngineer.firstChild → Intern1 → nextSibling → Intern2 → null
```

---

### 2️⃣ TeamTree.java - منطق درخت

**نقش:** مدیریت درخت: درج، حذف، نمایش، جستجوی DFS و BFS.

#### **الف) اولیه‌سازی:**

```java
private TeamMember root;

public TeamTree() {
    root = null;
}
```

**توضیح:**
- `root`: ریشهٔ درخت (معمولاً مدیر کل)
- ابتدا `null` است (درخت خالی)

#### **ب) درج - اضافه کردن عضو جدید:**

```java
public void insert(String parentName, String childName) {
    // **خط 1: اگر ریشه خالی است، فرزند ریشه می‌شود**
    if (childName == null) {
        if (root == null) {
            root = new TeamMember(parentName);
        }
        return;
    }
    
    // **خط 2: والد را پیدا کن**
    TeamMember parent = searchDFSNode(root, parentName);
    
    // **خط 3: اگر والد وجود ندارد، خطا**
    if (parent == null) {
        System.out.println("Parent " + parentName + " does not exist");
        return;
    }
    
    // **خط 4-10: عضو جدید را اضافه کن**
    TeamMember child = new TeamMember(childName);
    
    if (parent.firstChild == null) {
        // **خط 5: اگر فرزندی نیست، این اول است**
        parent.firstChild = child;
    } else {
        // **خط 6-10: به انتهای برادران اضافه کن**
        TeamMember sibling = parent.firstChild;
        while (sibling.nextSibling != null) {
            sibling = sibling.nextSibling;
        }
        sibling.nextSibling = child;
    }
}

private TeamMember searchDFSNode(TeamMember node, String target) {
    if (node == null) return null;
    
    if (node.name.equals(target)) {
        return node;
    }
    
    // **جستجو در فرزند اول**
    TeamMember result = searchDFSNode(node.firstChild, target);
    if (result != null) return result;
    
    // **جستجو در برادر بعدی**
    return searchDFSNode(node.nextSibling, target);
}
```

**توضیح خط به خط:**
1. اگر `childName == null`: این ریشه است (اگر درخت خالی باشد)
2. والد را با جستجوی DFS پیدا کن
3. اگر پیدا نشد: پیام خطا
4-10. عضو جدید ایجاد کن و به انتهای برادران اضافه کن
   - اگر `firstChild == null`: این اول است
   - وگرنه: به انتهای لیست برادران برو و آنجا اضافه کن

#### **ج) نمایش - چاپ ساختار تیم:**

```java
public void display() {
    displayNode(root);
}

private void displayNode(TeamMember node) {
    if (node == null) return;  // **خط 1: پایان**
    
    // **خط 2-8: نمایش نود و فرزندان مستقیمش**
    System.out.print(node.name + ":");
    
    TeamMember child = node.firstChild;
    boolean first = true;
    
    while (child != null) {
        if (!first) System.out.print(",");  // **خط 3: جداکننده**
        System.out.print(" " + child.name);
        first = false;
        child = child.nextSibling;  // **خط 4: برادر بعدی**
    }
    System.out.println();  // **خط 5: خط جدید**
    
    // **خط 6-9: نمایش بازگشتی برای تمام فرزندان**
    child = node.firstChild;
    while (child != null) {
        displayNode(child);  // **خط 7: فرزند بعدی**
        child = child.nextSibling;
    }
}
```

**توضیح خط به خط:**
1. اگر نود null: پایان
2. نام نود را چاپ کن
3-5. تمام فرزندان مستقیم را با `,` جدا‌شده چاپ کن
6-9. بازگشتی برای تمام فرزندان

**مثال خروجی:**
```
Producer: Editor,Host,SoundEngineer
Editor: ScriptWriter
Host:
SoundEngineer: Intern1,Intern2
```

#### **د) جستجوی عمق‌اول (DFS):**

```java
public void searchDFS(String name) {
    String path = searchDFSPath(root, name, "");  // **خط 1: جستجو**
    if (path == null) {
        System.out.println("Node " + name + " NOT found");  // **خط 2: نیافت**
    } else {
        System.out.println("Path (DFS): " + path);  // **خط 3: یافت**
    }
}

private String searchDFSPath(TeamMember node, String target, String path) {
    if (node == null) return null;  // **خط 1: نود خالی**
    
    // **خط 2: مسیر فعلی را آپدیت کن**
    String currentPath = path.isEmpty() ? node.name : path + " -> " + node.name;
    
    // **خط 3: اگر این نود هدف است**
    if (node.name.equals(target)) {
        return currentPath;
    }
    
    // **خط 4: جستجو در فرزند اول**
    String result = searchDFSPath(node.firstChild, target, currentPath);
    if (result != null) return result;  // **خط 5: اگر پیدا شد**
    
    // **خط 6: جستجو در برادر بعدی**
    return searchDFSPath(node.nextSibling, target, path);
}
```

**توضیح خط به خط:**
1. `searchDFSPath` را فراخوانی کن
2. اگر null برگشت: نیافت
3. وگرنه: مسیر را نمایش بده
- `searchDFSPath`: مسیر را رشته‌ای ساختار برگردان
1. اگر نود null: return null
2. مسیر فعلی را تولید کن (از ریشه تا اینجا)
3. اگر این نود هدف است: مسیر را برگردان
4-5. فرزند اول را جستجو کن
6. برادر بعدی را جستجو کن

**خروجی:**
```
Path (DFS): Producer -> Editor -> ScriptWriter
```

#### **ه) جستجوی عرض‌اول (BFS):**

```java
public void searchBFS(String name) {
    String path = searchBFSPath(name);
    if (path == null) {
        System.out.println("Node " + name + " NOT found");
    } else {
        System.out.println("Path (BFS): " + path);
    }
}

private String searchBFSPath(String target) {
    if (root == null) return null;
    
    return bfsLevel(root, target);
}

private String bfsLevel(TeamMember node, String target) {
    if (node == null) return null;  // **خط 1: نود خالی**
    
    // **خط 2: اگر این نود هدف است**
    if (node.name.equals(target)) {
        return node.name;
    }
    
    // **خط 3: بررسی تمام فرزندان مستقیم**
    TeamMember child = node.firstChild;
    while (child != null) {
        if (child.name.equals(target)) {
            return node.name + " -> " + child.name;  // **خط 4: فرزند مستقیم**
        }
        child = child.nextSibling;
    }
    
    // **خط 5: جستجو در فرزندان فرزندان**
    child = node.firstChild;
    while (child != null) {
        String result = bfsLevel(child, target);  // **خط 6: بازگشتی**
        if (result != null) {
            return node.name + " -> " + result;  // **خط 7: مسیر را کامل کن**
        }
        child = child.nextSibling;
    }
    
    return null;  // **خط 8: نیافت**
}
```

**توضیح خط به خط:**
1. اگر نود null: return null
2. اگر این نود هدف است: بازگردان
3-4. تمام فرزندان مستقیم را بررسی کن
5-7. اگر در فرزندان نیست، بازگشتی‌اً در فرزندان فرزندان جستجو کن
8. اگر پیدا نشد: null

**توضیح:** در BFS ساده‌شدهٔ ما، سطح به سطح جستجو می‌کنیم.

#### **و) حذف - حذف عضو و زیردستان:**

```java
public void delete(String name) {
    // **خط 1: اگر ریشه است**
    if (root != null && root.name.equals(name)) {
        root = null;
        System.out.println("Node " + name + " and its subtree deleted");
        return;
    }
    
    // **خط 2: در بقیهٔ درخت جستجو کن**
    deleteRecursive(root, name);
}

private void deleteRecursive(TeamMember parent, String target) {
    if (parent == null) return;  // **خط 1: والد خالی**
    
    // **خط 2-10: فرزند اول را بررسی کن**
    if (parent.firstChild != null && parent.firstChild.name.equals(target)) {
        parent.firstChild = parent.firstChild.nextSibling;  // **خط 3: ببر کن**
        System.out.println("Node " + target + " and its subtree deleted");
        return;
    }
    
    // **خط 4-10: برادران را بررسی کن**
    TeamMember sibling = parent.firstChild;
    while (sibling != null) {
        if (sibling.nextSibling != null && sibling.nextSibling.name.equals(target)) {
            sibling.nextSibling = sibling.nextSibling.nextSibling;  // **خط 5: ببر کن**
            System.out.println("Node " + target + " and its subtree deleted");
            return;
        }
        sibling = sibling.nextSibling;
    }
    
    // **خط 6: بازگشتی برای تمام فرزندان**
    TeamMember child = parent.firstChild;
    while (child != null) {
        deleteRecursive(child, target);
        child = child.nextSibling;
    }
}
```

**توضیح خط به خط:**
1. اگر ریشه است: ریشه را null کن
2. والد را نگه‌دار
3-4. فرزند اول والد را بررسی کن
5-6. اگر برابر است: nextSibling خودش را جایگزین کن (حذف کن)
7-10. برادران را بررسی کن
11-12. بازگشتی برای تمام فرزندان

**مثال:**
```
Before:  Producer: Editor,Host,SoundEngineer
Delete "Host":
After:   Producer: Editor,SoundEngineer
```

---

### 3️⃣ Phase3Main.java - واسط کاربر

**نقش:** دریافت دستورات و مدیریت درخت.

**کد کامل:**

```java
import java.util.Scanner;

public class Phase3Main {
    public static void main(String[] args) {
        TeamTree tree = new TeamTree();         // **خط 1: درخت جدید**
        Scanner sc = new Scanner(System.in);    // **خط 2: ورودی**
        
        while (sc.hasNext()) {
            String command = sc.next();  // **خط 3: دستور**
            
            // **خط 4-7: دستور "insert"**
            if (command.equals("insert")) {
                String parent = sc.next();      // **خط 4: والد**
                String child = sc.next();       // **خط 5: فرزند**
                tree.insert(parent, child);     // **خط 6: درج**
            }
            // **خط 8-10: دستور "display"**
            else if (command.equals("display")) {
                tree.display();  // **خط 8: نمایش**
            }
            // **خط 11-14: دستور "search-dfs"**
            else if (command.equals("search")) {
                String type = sc.next();         // **خط 11: نوع (DFS یا BFS)**
                String name = sc.next();         // **خط 12: نام**
                
                if (type.equals("DFS")) {
                    tree.searchDFS(name);        // **خط 13: جستجوی DFS**
                } else if (type.equals("BFS")) {
                    tree.searchBFS(name);        // **خط 14: جستجوی BFS**
                }
            }
            // **خط 15-18: دستور "delete"**
            else if (command.equals("delete")) {
                String name = sc.next();        // **خط 15: نام حذف‌شونده**
                tree.delete(name);              // **خط 16: حذف**
            }
        }
    }
}
```

**توضیح خط به خط:**
1. درخت جدید ایجاد کن
2. ورودی را اولیه‌سازی کن
3. حلقهٔ ورودی: دستور را بخوان
4-6. اگر "insert": والد و فرزند را بخوان و اضافه کن
8-10. اگر "display": درخت را نمایش بده
11-14. اگر "search": نوع (DFS/BFS) و نام را بخوان
15-16. اگر "delete": نام را بخوان و حذف کن

---

## 🧪 نحوه اجرا و تست کردن

### **مرحلهٔ 1: کامپایل کردن**

در پوشهٔ `src`:

```bash
javac phase3_team/*.java
```

### **مرحلهٔ 2: اجرا کردن**

```bash
java phase3_team.Phase3Main
```

---

### **نمونهٔ تست ۱: ساخت ساختار تیم**

**ورودی:**
```
insert Producer
insert Producer Editor
insert Producer Host
insert Producer SoundEngineer
insert Editor ScriptWriter
display
search DFS ScriptWriter
search BFS Host
```

**خروجی مورد انتظار:**
```
Producer: Editor,Host,SoundEngineer
Editor: ScriptWriter
Host:
SoundEngineer:
ScriptWriter:

Path (DFS): Producer -> Editor -> ScriptWriter
Path (BFS): Producer -> Host
```

**توضیح:**
- ریشه Producer ایجاد شود
- 3 عضو زیر Producer اضافه شوند
- 1 عضو زیر Editor اضافه شود
- نمایش ساختار
- جستجوی DFS: مسیر Producer → Editor → ScriptWriter
- جستجوی BFS: مسیر Producer → Host

---

### **نمونهٔ تست ۲: حذف عضو و فرزندان**

**ورودی:**
```
insert Producer
insert Producer Editor
insert Producer Host
insert Editor ScriptWriter
insert ScriptWriter AssistantWriter
delete Editor
display
search DFS ScriptWriter
```

**خروجی مورد انتظار:**
```
Node Editor and its subtree deleted

Producer: Host
Host:

Node ScriptWriter NOT found
```

**توضیح:**
- بعد از حذف Editor، تمام فرزندان آن (ScriptWriter و AssistantWriter) حذف می‌شوند
- ScriptWriter دیگر پیدا نمی‌شود

---

### **نمونهٔ تست ۳: خطای والد**

**ورودی:**
```
insert Producer
insert Producer Editor
insert NonExistent Child
display
```

**خروجی مورد انتظار:**
```
Parent NonExistent does not exist

Producer: Editor
Editor:
```

**توضیح:**
- Non Existent والد وجود ندارد → خطا
- فقط Producer و Editor موجودند

---

## ⚠️ نکات مهم

1. **First-Child/Next-Sibling:** یادتان باشد که nextSibling فقط برادران را متصل می‌کند
2. **DFS:** عمق‌اول می‌رود (یک شاخه را تا انتها دنبال می‌کند)
3. **BFS:** عرض‌اول می‌رود (سطح به سطح)
4. **حذف:** تمام زیردستان حذف می‌شوند

---

## ✅ خلاصهٔ فاز سوم

- ✅ درخت عمومی با First-Child/Next-Sibling دستی‌پیاده‌سازی‌شده
- ✅ درج و حذف صحیح (حذف زیردستان نیز)
- ✅ جستجوی DFS و BFS هردو پیاده‌شده
- ✅ مسیرهای صحیح نمایش داده می‌شوند
- ✅ بدون کتابخانه‌های آماده

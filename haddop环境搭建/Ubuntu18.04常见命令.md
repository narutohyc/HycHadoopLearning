# Hadoop伪分布环境搭建

<img src="http://upload-images.jianshu.io/upload_images/15675864-952291e89189c8a8.jpg">

这里介绍了在Ubuntu18.04下的常用Ubuntu命令：

https://blog.csdn.net/zxs9999/article/details/79118466

***

1. **获取Ubuntu版本号**：  /etc/issue 或 lsb_release -a

2. **查看Ubuntu系统位数**：uname  -ar 或 getconf LONG_BIT

3. **查看Ubuntu机器的处理器架构**，结果为i686或x86_64：arch 或uname  -m

4. **显示当前目录内容(目录清单)**，(如果按文件改动时间顺序来排序，则可用ls  -lt)： ls

5. **显示当前目录下文件的详细信息**，包括读写权限，文件大小，文件生成日期等(若想按照更改的时间先后排序，则需加-t参数，ll  -t或ll  -t  |tac，后者为最新修改的时间排在最后)：ll

6. **改变当前工作目录位置**，(若进入系统根目录，可直接使用cd  /) ：cd  directory_name ，若文件夹有空格，则需用引号括起来，如进入目录名为a b的目录，则为：cd  “a b”

7. **删除当前目录中指定文件**，如删除.xxx的所有文件，(对于链接文件，只是删除了链接，原有文件均保持不变；如果没有使用-r选项，则rm不会删除目录；如果想删除前再次获取确认，可使用-i选项，如rm  -i  xxx)：rm  *.xxx

8. **删除空目录**，一个目录被删除之前必须是空的，删除某目录时也必须具有对父目录的写权限：rmdir  xxx

9. **移动文件**(若将文件xx.yy移动到目录tt中，则为mv  xx.yy tt)或**将文件改名**(若将文件名xx.yy改为aa.bb，则为mv  xx.yy aa.bb)：mv  源文件或目录  目标文件或目录

10.  **复制文件**(可同时有多个文件)或目录到指定的目录(若将目录aa复制到bb目录中，则为：cp  -a aa  bb)：cp  源文件或源目录  目的目录

11. **建立新目录**，要求创建目录的用户在当前目录中具有写权限，并且指定的目录名不能是当前目录中已有的目录，(如果创建权限为777的目录，可用mkdir  -m 777  filename)：mkdir  filename

12. **查看某个指令的详细说明**，如查看rm指令：man  rm

13. **创建静态库**，如使用当前目录中的所有.o文件创建libxx.a静态库：ar  -r  libxx.a *.o

14. **创建动态库**，如使用当前目录中的所有.o文件创建libxx.so动态库(若有两个在不同目录的.cpp文件直接生成动态库，则为：gcc  -shared/home/spring/gdbtest/src/add/add.cpp/home/spring/gdbtest/src/subtract/subtract.cpp -o  libtest.so)(若从静态库b1.a,b2,a直接生成动态库b.so，则相应命令为：gcc-shared -fPIC -Wl,--whole-archive b1.a  b2.a  -Wl,--no-whole-archive -o  b.so)：gcc  -shared –o  libxx.so  *.o

15. **查看当前所处路径**(完整路径)：pwd

16. **需要提示权限执行的命令**，如果你不是root用户，那么在执行一些命令做一些操作的时候有时是不允许的，此时可以在命令前面加上sudo：sudo

17. **查看静态库是否含有某个函数**，如查看xx.a静态库中是否有fun1函数(也可以查看某个库所有包含的函数，如nm  xx.a >fun.txt)：nm  xx.a | grep fun1

    **查看动态库是否含有某个函数**，如查看yy.so动态库中是否有fun2函数(也可以查看某个库所有包含的函数，如 nm yy.so > info.txt)：nm yy.so | grep fun2  

    > 如果仅导出已定义的符号，则加上"-D"选项，如nm -D yy.so | grep fun2

18. **查看cpuinfo相关信息**：cat  /proc/cpuinfo

19. **显示系统日期**：date

20. cal命令用于**查看公历(阳历)日历**，可以查看指定年份的，如cal  -y 2013, 也可以查看当前月的，直接用cal，无需带任何参数。

21. **关闭系统**(若非root，则shutdown前需加sudo)：shutdown  -h  now

22. **重启系统**(若非root，则shutdown前需加sudo)：shutdown  -r  now

23. **与windows共享目录命令**，如共享目录为test(<http://download.csdn.net/detail/jiaoxiaogu/7309181> )：cd  /mnt/hgfs/test

24. **创建一个指向文件或目录的软连接**(当我们需要在不同的目录，用到相同的文件时，我们不需要在每一个需要的目录下都放一个必须相同的文件，我们只要在某个固定的目录，放上该文件，然后在其它的目录下用ln命令链接(link)它就可以，不必重复的占用磁盘空间)。格式，ln  -s  源文件 目标文件。文件保持同步变化。目录只能创建软链接，目录创建链接必须用绝对路径，在链接目标目录中修改文件都会在源文件目录中同步变化。

    > 如给源目录/home/spring/aa创建一个新的目录/usr/local/aa：ln  -s /home/spring/aa  /usr/local/aa

25. **更改文件或目录的日期时间**，包括存取时间和更改时间(若有两个文件a.b,c.e，将c.e的文件的日期修改成a.b的日期，则为touch  a.b c.e)，或者新建一个不存在的文件(若将已存在的aa.x文件新建成一个bb.y文件(bb.y文件原始是不存在的)，并且保持与aa.x文件的内容、创建修改时间一致，则touch  aa.x bb.y)：touch  源文件名  目的文件名

26. **显示文件内容**(若显示aa.txt的文件内容，则为cat  aa.txt)，或者将几个文件连接起来显示：cat  filename

27. **将输出的文件内容自动的加上行号**：nl  filename

28. **以一页一页的方式显示文件的内容**，按空格键(space)为显示下一页；按b键会往回(back)一页显示；按q键退出more：more  filename

29. **分页显示文件的内容**，类似于more，但比more功能更强大；按q键退出less；若搜索文件中某个字符串xxx，可使用/xxx来做到；通过加入-N参数来显示每行的行号：less  参数  filename

30. **用来显示文件的开头内容**：head  filename

31. **用来显示文件的末尾内容**：tail  filename

32. **查看某个系统命令是否存在**或者查看某个可执行文件的位置，若查找ls命令的位置，则which  ls， 会返回/bin/ls，若找不到该命令(或该执行文件)，则不会有任何返回结果：which  commandname

33. **在当前目录查找文件名**后缀为txt的所有文件(若从系统根目录查找，则为find  / -name  “*.txt”；若从你的$HOME目录查找，则为find  ~ -name  “*.txt”；在/etc目录查找，则为find  /etc -name  “*.txt”；若要在当前目录查找文件名以一个小写字母开头，最后是4到9加上.log结束的文件，则为find  .  –name  “[a-z]*[4-9].log”)：find  .  -name  “*.txt” 

    **在当前目录查找此目录下所有文件内包含的指定关键字**"ab"，则执行：$ find . -type f -print -exec grep ab {} \;

34. chmod命令用于**改变linux系统文件或目录的访问权限**。

    Linux系统中的每个文件和目录都有访问许可权限，用它来确定谁可以通过何种方式对文件和目录进行访问和操作。文件或目录的访问权限分为只读、只写和可执行三种。有三种不同类型的用户可对文件或目录进行访问，文件所有者(一般是文件的创建者)，同组用户，其他用户。每一文件或目录的访问权限都有三组，每组用三位来表示，分别为文件属主的读、写和执行权限；与属主同组的用户的读、写和执行权限；系统中其他用户的读、写和执行权限。当用ls  -l命令显示文件或目录的详细信息时，最左边的一列为文件的访问权限。第一列共有10个位置，第一个字符指定了文件类型。在通常意义上，一个目录也是一个文件。如果第一个字符是横线，表示是一个非目录的文件。如果是d，表示是一个目录。从第二个字符开始到第十个共9个字符，3个字符一组，分别表示了3组用户对文件或者目录的权限。权限字符用横线代表空许可，r代表只读，w代表写，x代表可执行。确定了一个文件的访问权限后,用户可以利用chmod命令来重新设定不同的访问权限。权限代号：r:读权限，用数字4表示；w:写权限，用数字2表示；x:执行权限，用数字1表示；-:删除权限，用数字0表示；s:特殊权限。如对目录test及其子目录所有文件添加可读可写可执行权限，则为：chmod  -R 777  test

35. 可以用tar命令进行**压缩、解压缩、打包、解包**等。

    打包是指将一大堆文件或目录变成一个总的文件；压缩则是将一个大的文件通过一些压缩算法变成一个小文件。使用tar程序打出来的包常称为tar包，tar包文件的命令通常都是以.tar结尾的。生成tar包后，就可以用其它的程序来进行压缩。常用参数：-c:建立新的压缩文件；-x:从压缩的文件中提取文件；-z:支持gzip解压文件；-j:支持bzip2解压文件；-Z:支持compress解压文件；-v:显示操作过程；-f:指定压缩文件。如，有个目录test1,将其打包则为:tar  cvf test1.tar  test1;若将其打包并以gzip压缩，则为:tar  zcvf  est1.tar.gz  test1; 若解包或解压缩，则相应的把cvf和zcvf换成xvf和zxvf即可，如把test2.tar.gz解压缩，则为：tar  xvf est2.tar.gz ,则会在当前目录下生成一个test2文件。

36. **查看文件或目录磁盘使用的空间大小**，可以用du命令，如显示test目录大小，则将终端定位到test目录后，输入:du  -s

37. diff能**比较单个文件或者目录内容**。如果指定比较的是文件,则只有当输入为文本文件时才有效.以逐行的方式，比较文本文件的异同处。如果指定比较的是目录的时候，diff命令会比较两个目录下名字相同的文本文件。列出不同的二进制文件、公共子目录和只在一个目录出现的文件。如比较1.txt和2.txt两个文本文件，可为：diff  -c 1.txt  2.txt ,会将内容不同的地方在行之前用”!”标出。

38. grep命令是一种强大的**文本搜索工具**。如从test.txt文件中查找android关键词，则为：grep  -n  ‘android’  test.txt . 也可以同时从多个文件中查找关键词，如：grep  -n  ‘android’  test1.txt test2.txt 如果要在当前目录下所有文件查找"AB"字符串：$ grep -rn "AB" *

39. wc命令用于**统计指定文件中的行数、字数、字节数**，并将统计结果[显示输出](https://www.baidu.com/s?wd=%E6%98%BE%E7%A4%BA%E8%BE%93%E5%87%BA&tn=24004469_oem_dg&rsv_dl=gh_pl_sl_csd)。如:wc  test.txt ,输出为8  9 10  test.txt , 其中8表示行数，9表示字数，10表示字节数，test.txt表示文件名。如果统计当前目录下带有后缀名为frm、asp、bas的代码行数，则命令为find  . -name  “*.frm”  -or -name “*.bas”  -or -name “*.asp”  | xargs wc  -l > result.txt ，执行完此命令后会在当前目录下生成一个result.txt文件，里面包含了每个文件(frm/bas/asp)的代码行数以及总的代码行数。

40. free命令可以**显示Linux系统中空闲的、已用的物理内存**及swap内存，及被内核使用的buffer.如:free  -m ,则会以MB为单位显示内存使用情况。

41. 可以使用apt-get命令来**安装/更新一个deb包**，如安装g++，则：apt-get  install g++

42. **c++filt命令**：C++函数在Linux系统下编译之后会变成类似这样：_ZN9CBC_EAN1312RenderBitmapERP12CFX_DIBitmapRi，乍一看并不知道函数的原始名称，此时可以用c++filt命令：c++filt  _ZN9CBC_EAN1312RenderBitmapERP12CFX_DIBitmapRi，即可显示原始函数名称CBC_EAN13::RenderBitmap(CFX_DIBitmap*&,int&)

43. echo命令是内建的shell命令，用于**显示变量的值或者打印一行文本**，如可以通过输入echo  $PATH ,查看由哪些文件目录加入到了系统环境变量中(PATH前面的$表示后面接的是变量)，可以通过输入echo  2 >/home/spring/1.txt ,将2写入到1.txt文件中

44. rar命令可以**解压缩在windwos下生成的rar文件**，如test.rar，则可以执行：$rar  x test.rar

45. ps -ef命令用来**查看Linux系统所有进程**，如果想查看包含指定名字(如 test)的进程，则可以执行：$ ps -ef | grep test

46. **杀死指定的进程**可以用kill命令，如杀死进程PID为30732的进程，则可以执行：$ kill -9 30732 ，若需要批量杀死进程，则可执行：$ ps aux | grep test | awk ‘{print $2}’ | xargs kill -9

47. **查看Linux系统CPU和内存使用率**，可以通过top命令：即先在终端输入top命令回车，然后再按1即可

48. **通过命令打开显示图像**：$ eog  /images/a.jpg

49. **远程拷贝文件**： 可以通过"scp"命令，如从远程服务器传某个指定目录或文件到本地指定目录，则执行：$ scp -r  xxx  spring@10.0.0.66:/home/spring/yyy/   ;若存放到本地根目录，可以执行：$ scp -r  a.jpg  spring@10.0.0.66:~/     ;执行以上命令时会要求输入本地机子的密码，若不想每次都输入，可以执行：$ ssh-copy-id  spriing@10.0.0.66  ，这样下次在传文件时就不用重复输入密码了；若从本地传文件到远程服务器，则执行：$ scp -r  b.jpg  autobuild@10.0.8.88:/home/autobuild/fbc/

50. **查看机子ip和MAC地址**：$ ifconfig

51. **安装SSH-SERVER**：$ sudo  apt-get install -y openssh-server

    可以通过ssh命令访问指定的ubuntu主机，假设要访问的ubuntu主机名为spring, ip为10.0.1.1，又知道此主机的密码，则执行：ssh spring@10.0.1.1 ，回车，再收入这台主机的密码即可

52. **安装Google Chrome**，http://mp.blog.csdn.net/postedit/79101856

53. **解决弹出对话框"System program problem detected Do you want to report the problem now?"的问题**，在终端输入：$ sudo rm /var/crash/* 

54. **在Ubuntu上查看指定文件是采用何种编码方式可以使用file命令**，如查找test目录下所有的.cpp文件的编码格式，则执行：$ file test/*.cpp

55. **在Ubuntu上安装.deb文件命令**,如code_1.13.0-1496940180_amd64.deb： $ sudo  dpkg  -i  code_1.13.0-1496940180_amd64.deb

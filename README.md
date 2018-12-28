# HycHadoopLearning
关于hadoop环境配置与学习



***

向GitHub提交代码步骤（https://blog.csdn.net/HJ_CQ/article/details/73201250）

方式一：

1.首先在GitHub上创建一个仓库，把地址复制下来，如：https://github.com/my/test.git

2.右键桌面打开GitBash，cd到要提交的项目根目录下，输入git init命令初始化仓库，在项目文件夹下出现.git文件夹

3.关联远程仓库：git remote add origin https://github.com/my/test.git（后面是你的仓库地址）

4.使用命令git add . 添加所有文件到暂存区---在修改代码之后，可以使用git add MainActivity.java（后面是你修改过的文件） 添加修改的文件到暂存区

5.git commit -m "first commit"，提交代码

6.我们可以通过命令git status来查看是否还有文件未提交，如果有红色文字出现，说明还有文件未提交

7.git push -u origin master （推送到远程仓库），由于远程库是空的，我们第一次推送master分支时，加上了 –u参数，Git不但会把本地的master分支内容推送到远程新的master分支，还会把本地的master分支和远程的master分支关联起来，在以后的推送或者拉取时，只要做了提交就可以使用命令git push origin master进行推送。



方式二：

向大家推荐一种郭霖大神的方法：

1.首先仍然是先在GitHub上创建一个仓库，把地址复制下来，如：https://github.com/my/test.git

2.在GitBash进入本地项目的根路径，使用git clone https://github.com/my/test.git 把远程仓库克隆到本地，克隆之后在项目根路径多了一个跟本地项目名称一样的文件夹，打开之后里面会有一个.git文件夹（通常是隐藏文件）。把这个.git文件夹剪切到项目的根路径（就是上一级路径）

3.接下来就一样了，git add . 添加项目到暂存区

4.git commit -m "first commit" ，提交本地代码

5.git push origin master 推送到GitHub

其他常用命令：

① git diff app/src/main/java/com/example/providertest/MainActivity.java （查询修改内容） 

② cat app/src/main/java/com/example/providertest/MainActivity.java（查询文件内容）

③ git log（查看历史记录）

④ git reset  –hard HEAD^（版本回退操作，如果要退回到上上个版本，使用HEAD^^，如果要回退100个版本就用命令：git reset  --hard HEAD~100）

⑤ git reset  --hard 版本号（回退到某一个版本，版本号如：6a5cad---可以先通过如下命令获取到版本号：git reflog ）

⑥ git checkout –MainActivity.java （意思就是，把MainActivity.java文件在工作区做的修改全部撤销）

⑦ rm MainActivity.java（删除文件）

----




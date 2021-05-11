import sys
import os
import requests
import re
import urllib
from lxml import html

# 获取商品基本信息（除了价格）
def getInfo(url):
    # requests请求网页，得到网页源代码
    kv = {'user-agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.128 Safari/537.36'}

    r = requests.get(url, headers=kv).content
    # 调用html.fromString函数解析html源代码
    sel = html.fromstring(r)
    # print("\n")
    # print(sel)
    # myWant = html.tostring(sel, encoding='utf-8').decode('utf-8')
    # print("\n")
    # print(myWant)
    # wants = sel.xpath("/html/head/script/text()")
    # print(wants)

    # 获取完整名称，text()获取该标签下的文本
    fullName = sel.xpath("//div[@class='sku-name']/text()")
    #fullName = sel.xpath("/html/body/div[6]/div/div[2]/div[1]/text()")



    if(fullName[0] is not None):
        matchObj = re.match(r'\s*(.+)\s*', fullName[0])     #截取非空白字符
        if(matchObj):
            fullName = matchObj.group(1)
    else:
        fullName = ''
    print(fullName)

    # 提取商家信息
    shop = sel.xpath("//*[@id='crumb-wrap']/div/div[2]/div[2]/div[1]/div/a/text()")[0]
    #shop = sel.xpath("/html/body/div[5]/div/div[2]/div[2]/div[1]/div/a/text()")[0]
    if(shop is None):
        shop = ''
    print(shop)

    # 获取图片地址
    imgUrl1 = sel.xpath("//*[@id='spec-n1']//img/@src")
    #imgUrl1 = sel.xpath("/html/body/div[6]/div/div[1]/div/div[1]/img/@src")

    imgUrl2 = sel.xpath("//*[@id='spec-n1']//img/@data-origin")
    #imgUrl2 = sel.xpath("/html/body/div[6]/div/div[1]/div/div[1]/img/@data-origin")
    print(imgUrl1)
    print(imgUrl2)
    if(imgUrl1 == []):
        imgUrl = imgUrl2[0]
    elif(imgUrl2 == []):
        imgUrl = imgUrl1[0]
    else:
        imgUrl = ''
    print(imgUrl)

    # 将结果写入文件
    if os.path.exists('jd_info.txt'):
        os.remove('jd_info.txt')
    with open(f'jd_info.txt','x', encoding='utf8') as text_file:
        text_file.write(fullName + '\n')
        text_file.write(shop + '\n')
        text_file.write(imgUrl + '\n')

def getPicture(html):
    #urls = re.findall('<li ><img alt=(.*?) data-url', html)
    reg = r'src="\/\/(.+?\.jpg)"'
    imgre = re.compile(reg)
    imglist = re.findall(imgre, html)
    #print(imglist)
    x = 0
    for imgurl in imglist:
        print(imgurl)
        #urllib.request.urlretrieve('https://'+imgurl, '%s.jpg' % x)
        x += 1
    return imglist


if __name__ == "__main__":
    #url = 'https://item.jd.com/71810342874.html'
    url = sys.argv[1]
    getInfo(url)
    #getPicture(html)
#html = response.read().decode('utf-8')   #调用read()进行读取，转换为utf-8的编码

#html1 = urllib.request.urlopen(url).read()
#html1 = str(html1)
#print(html)  #打印



#解析网页
#dir_name = re.findall('<title>(.*?)</title>', html)[-1]
#print(dir_name)
#if not os.path.exists(dir_name):
 #   os.mkdir(dir_name)

#urls = re.findall('<img src="(.*?)" class="focus_img">', html)
#print(urls)
#print(urls)

#保存图片
#for url in urls:
 #   time.sleep(1)
  #  file_name = url.split('/')[-1]
   # response = requests.get(url, headers=headers)
    #with open(dir_name +'/' + file_name, 'wb') as f:
     #   f.write(response.content)

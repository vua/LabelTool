# LabelTool
## Target Detection Label Tool (Label Template can be customized)

![logo](https://github.com/vua/LabelTool/blob/master/resource/sample%20map/map2.png)
### 1. Label Template
  * #### General Template
    We have provided two general templates, `pascal` and `coco`,which you can select from the radio box.
  * #### Custom Template
    >You can define your own template in `resource/diy.txt` using `ten variables`, `a repeat character`, and strings
    [variables List]:
    ```
    $0:xmin
    $1:ymin
    $2:xmax
    $3:ymax
    $4:label
    $5:file name
    $6:absolute path
    $7:image width
    $8:image height
    $9:line separator
    ```
    >You can use multi-line annotation symbols to mark repetitive content
    ```
    \* repetitive content *\
    ```
    so,you can create pascal template like 
    ```
    <?xml version="1.0" encoding="UTF-8"?>$9
    <annotation>$9
      <folder>VOC2007</folder>$9
      <filename>$5</filename>$9
      <size>$9
        <width>$7</width>$9
        <height>$8</height>$9
        <depth>3</depth>$9
      </size>$9
      /*<object>$9
        <name>$4</name>$9
        <bndbox>$9
          <xmin>$0</xmin>$9
          <ymin>$1</ymin>$9
          <xmax>$2</xmax>$9
          <ymax>$3</ymax>$9
        </bndbox>$9
      </object>$9*/
    </annotation>$9
    ```
    or create a custom template like 
    ```
    Path:$6$9
    Name:$5$9
    Size( $7 $8 )$9
    Object:$9
    /*  Item:$4 Loc{ $0 $1 $2 $3 } $9*/
    ```
    using this template, we can get file like 
    ```
    Path:C:/Users/53121/Desktop/IMAGE/01.jpg
    Name:01.jpg
    Size( 450 300 )
    Object:
      Item:bird Loc{ 358 33 423 99 } 
      Item:cat Loc{ 28 120 335 280 }
    ```
    

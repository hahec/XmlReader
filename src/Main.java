import entity.RspPerson;

public class Main {
    public static void main(String[] args) {
        String xmlStr = "<fgj><message>0</message><ryxxlist><ryxx><xm>甲方1</xm><zjlx>身份证1</zjlx><sfzmh>440412154894</sfzmh><lxdh>132456</lxdh><lxdz>利通广场</lxdz><dzyx>12306@qq.com</dzyx><yzbm>510000</yzbm></ryxx><ryxx><xm>甲方2</xm><zjlx>身份证2</zjlx><sfzmh>440412154894</sfzmh><lxdh>132456</lxdh><lxdz>利通广场2</lxdz><dzyx>12302@qq.com</dzyx><yzbm>510002</yzbm></ryxx></ryxxlist></fgj>";
        XmlReader reader = new XmlReader();
        RspPerson rsp = reader.parseToEntity(RspPerson.class, xmlStr);
        System.out.println(rsp.getRspState());


    }
}

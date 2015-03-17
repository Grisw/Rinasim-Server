package org.rinasim.object;

import java.io.Serializable;
import java.util.Date;

/**
 * �û�����
 * @date 2015��3��15�� ����8:36:31
 * @since v1.0
 */
public class User implements Serializable{

	private static final long serialVersionUID = -7474127554963548186L;
	
	private String id;
	private byte[] portrait;
	private String name;
	private String password;
	private String sex;
	private Date birthday;
	private String email;
	private String age;
	private String constellation;
	private String occupation;
	private String company;
	private String school;
	private String address;
	private String introduction;
	private String ip;
	private boolean status;
	
	/**
	 * ��ȡID
	 * @return �û�ID
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * �趨ID
	 * @param id �û�ID,1~4294967296
	 */
	public void setId(int id){	
		this.id=id+"";
	}
	
	/**
	 * ��ȡͷ��
	 * @return ͷ���ֽ�����
	 */
	public byte[] getPortrait() {
		return portrait;
	}

	/**
	 * ����ͷ��
	 * @param portrait Ҫ���õ�ͷ��Image�ֽ�����
	 */
	public void setPortrait(byte[] portrait) {
		this.portrait = portrait;
	}

	/**
	 * ��ȡ�û���
	 * @return �û���
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * �趨�û���
	 * @param nickname �û���,������15���ַ�
	 * @return true �޸ĳɹ���false �޸�ʧ��
	 */
	public boolean setName(String name){
		if(name.length()<1||name.length()>18){
			return false;
		}else{
			this.name=name;
			return true;
		}
	}
	
	/**
	 * ��ȡ����
	 * @return �û�����
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * �趨����
	 * @param password ����,������20���ַ�
	 * @return true �޸ĳɹ���false �޸�ʧ��
	 */
	public boolean setPassword(String password){
		if(password.length()<6||password.length()>20){
			return false;
		}else{
			this.password=password;
			return true;
		}
	}
	
	/**
	 * ��ȡ�Ա�
	 * @return �û��Ա�
	 */
	public String getSex(){
		return sex;
	}
	
	/**
	 * �趨�Ա�
	 * @param sex �Ա��л�Ů����
	 */
	public void setSex(String sex){
		this.sex=sex;
	}	
	
	/**
	 * ��ȡ����
	 * @return ����
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * ��������
	 * @param birthday Ҫ���õ�����
	 */
	public void setBirthday(Date birthday) {
		this.birthday=birthday;
	}

	/**
	 * ��ȡemail��ַ
	 * @return email��ַ
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * ����email��ַ
	 * @param email Ҫ���õ� email��ַ
	 * @return true �޸ĳɹ���false �޸�ʧ��
	 */
	public boolean setEmail(String email) {
		if(email.length()<=320&&email.matches("^\\w{1,64}@\\w{1,}\\.\\w{1,}$")){
			this.email=email;
			return true;
		}else{
			return false;
		}
	}

	/**
	 * ��ȡ��ַ
	 * @return �û���ַ
	 */
	public String getAddress(){
		return address;
	}
	
	/**
	 * �趨��ַ
	 * @param address ��ַ,������50���ַ�
	 * @return true �޸ĳɹ���false �޸�ʧ��
	 */
	public boolean setAddress(String address){
		if(address.length()>50){
			return false;
		}else{
			this.address=address;
			return true;
		}
	}
	
	/**
	 * ��ȡ����
	 * @return �û�����
	 */
	public String getAge(){
		return age;
	}
	
	/**
	 * �趨����
	 * @param age ����,1~100
	 * @return true �޸ĳɹ���false �޸�ʧ��
	 */
	public boolean setAge(int age){
		if(age<1||age>100){
			return false;
		}else{
			this.age=age+"";
			return true;
		}
	}
	
	/**
	 * ��ȡ����
	 * @return ����
	 */
	public String getConstellation() {
		return constellation;
	}

	/**
	 * ��������
	 * @param constellation Ҫ���õ�����
	 */
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	/**
	 * ��ȡְҵ
	 * @return ְҵ
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * ����ְҵ
	 * @param occupation Ҫ���õ�ְҵ
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	/**
	 * ��ȡ��˾
	 * @return ��˾����
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * ���ù�˾
	 * @param company Ҫ���õĹ�˾���ƣ����Ȳ�����50���ַ�
	 * @return true �޸ĳɹ���false �޸�ʧ��
	 */
	public boolean setCompany(String company) {
		if(company.length()>50){
			return false;
		}else{
			this.company = company;
			return true;
		}
	}

	/**
	 * ��ȡѧУ����
	 * @return ѧУ����
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * ����ѧУ����
	 * @param school Ҫ���õ�ѧУ���ƣ����Ȳ�����50���ַ�
	 * @return 
	 * @return true �޸ĳɹ���false �޸�ʧ��
	 */
	public boolean setSchool(String school) {
		if(school.length()>50){
			return false;
		}else{
			this.school = school;
			return true;
		}
	}

	/**
	 * ��ȡ����˵��
	 * @return ����˵��
	 */
	public String getIntroduction(){
		return introduction;
	}
	
	/**
	 * �趨����˵��
	 * @param remark ����˵��,������127���ַ�
	 * @return true �޸ĳɹ���false �޸�ʧ��
	 */
	public boolean setIntroduction(String introduction){
		if(introduction.length()>127){
			return false;
		}else{
			this.introduction=introduction;
			return true;
		}
	}
	
	/**
	 * ��ȡIP
	 * @return �û�IP
	 */
	public String getIp(){
		return ip;
	}
	
	/**
	 * �趨IP
	 * @param ip ���ϸ�ʽ��IP��ַ
	 */
	public void setIp(String ip){	
		this.ip=ip;
	}

	/**
	 * �Ƿ�����
	 * @return true ���ߣ�false ������
	 */
	public boolean isOnline() {
		return status;
	}

	/**
	 * ����״̬
	 * @param status true ���ߣ�false ������
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

}

package com.tch.filesystem.model;

public class FilePath {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_path.id
     *
     * @mbg.generated Tue Nov 19 01:22:11 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_path.url
     *
     * @mbg.generated Tue Nov 19 01:22:11 CST 2019
     */
    private String url;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file_path.uid
     *
     * @mbg.generated Tue Nov 19 01:22:11 CST 2019
     */
    private Integer uid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_path.id
     *
     * @return the value of file_path.id
     *
     * @mbg.generated Tue Nov 19 01:22:11 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_path.id
     *
     * @param id the value for file_path.id
     *
     * @mbg.generated Tue Nov 19 01:22:11 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_path.url
     *
     * @return the value of file_path.url
     *
     * @mbg.generated Tue Nov 19 01:22:11 CST 2019
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_path.url
     *
     * @param url the value for file_path.url
     *
     * @mbg.generated Tue Nov 19 01:22:11 CST 2019
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file_path.uid
     *
     * @return the value of file_path.uid
     *
     * @mbg.generated Tue Nov 19 01:22:11 CST 2019
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file_path.uid
     *
     * @param uid the value for file_path.uid
     *
     * @mbg.generated Tue Nov 19 01:22:11 CST 2019
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
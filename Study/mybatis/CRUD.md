## 模板操作



```xml
<insert id="saveUser" useGeneratedKeys="true" keyProperty="id"> <!--获取主键的值-->
    insert into user(username,password,address,phone)
    values (#{username},#{password},#{address},#{phone})
</insert>
```

```xml
<update id="updateUser"  >  <!--动态更新语句-->
    update user
    <set>
        <if test="username != null || username!=''">username = #{username},</if>
        <if test="password != null || password!=''">password = #{password},</if>
        <if test="address != null || address!=''">address = #{address},</if>
        <if test="phone != null || phone!=''">phone = #{phone},</if>
    </set>
    where id = #{id}
</update>
```

```xml
<insert id="batchInsert"  ><!--批量插入-->
    insert into user(username,password,address,phone)
    values
    <foreach collection="list" item="u" separator=",">
        (#{u.username},#{u.password},#{u.address},#{u.phone})
    </foreach>
</insert>
```

```xml
<select id="findByRange" resultMap="userMap" ><!--动态查询-->
    <include refid="findAllUser"/>
    <where>
        <if test="list != null and list.size()!=0">
            u.id in
            <foreach collection="list" index="i" item="item" open="(" separator="," close=")">
                #{item}
            </foreach>
        </if>
    </where>
</select>
```

```xml
<!--使用嵌套结果-->
<select id="queryAllByNestedResult" resultMap="allByNestedResult">
    select
        u.id uid ,username ,u.password userPassword ,u.phone,u.address,
        a.account_name accountName,a.password accountPassword,a.balance,
        m.member_name memberName,m.desc memberDesc
    from
        user u left JOIN account a on u.id = a.uid
               left join member m on u.id = m.uid;
</select>
<resultMap id="allByNestedResult" type="user">
    <id column="uid" property="id"/>
    <result column="username" property="username"/>
    <result column="password" property="password"/>
    <result column="phone" property="phone"/>
    <result column="address" property="address"/>
    <association property="account" >
        <result column="accountName" property="accountName"/>
        <result column="accountPassword" property="password"/>
        <result column="balance" property="balance"/>
    </association>
    <collection property="members" ofType="member">
        <result column="memberName" property="memberName"/>
        <result column="memberDesc" property="desc"/>
    </collection>
</resultMap>
<!--使用嵌套查询-->
<select id="queryAllByNestedQuery" resultMap="allByNestedQuery">
    select
           u.id uid ,username ,u.password userPassword ,u.phone,u.address
    from
           user u
</select>
<resultMap id="allByNestedQuery" type="user">
    <id column="uid" property="id"/>
    <result column="username" property="username"/>
    <result column="password" property="password"/>
    <result column="phone" property="phone"/>
    <result column="address" property="address"/>
    <association property="account" column="uid" select="com.ex.ssm.mapper.AccountMapper.queryAccountByUid" />
    <collection property="members" column="uid"  select="com.ex.ssm.mapper.MemberMapper.queryMemberByUid" />
</resultMap>
```
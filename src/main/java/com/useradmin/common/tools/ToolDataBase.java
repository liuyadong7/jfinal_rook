package com.useradmin.common.tools;

import com.jfinal.kit.PropKit;
import com.useradmin.common.constant.ConstantInit;
import com.useradmin.common.dto.DataBase;
import com.useradmin.common.enums.Driver;

/**
 * 数据库导入导出处理
 *
 * @author 刘亚东
 */
public abstract class ToolDataBase {

    /**
     * 分解数据库连接url
     *
     * @return
     */
    public static DataBase getDbInfo(Driver driver) {
        String driverClass = null;
        String ip = null;
        String port = null;
        String dbName = null;

        String jdbcUrl = PropKit.get(ConstantInit.jfinal_datasource_jdbcUrl);
        String userName = PropKit.get(ConstantInit.jfinal_datasource_user);
        String passWord = PropKit.get(ConstantInit.jfinal_datasource_password);

        switch (driver) {
            case postgresql:
                driverClass = "org.postgresql.Driver";

                // 解析数据库连接URL，获取数据库名称
                dbName = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
                dbName = dbName.substring(dbName.indexOf("/") + 1);

                // 解析数据库连接URL，获取数据库地址IP
                ip = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
                ip = ip.substring(0, ip.indexOf(":"));

                // 解析数据库连接URL，获取数据库地址端口
                port = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
                port = port.substring(port.indexOf(":") + 1, port.indexOf("/"));

                break;
            case mysql:
                driverClass = "com.mysql.jdbc.Driver";

                // 解析数据库连接URL，获取数据库名称
                dbName = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
                dbName = dbName.substring(dbName.indexOf("/") + 1, dbName.indexOf("?"));

                // 解析数据库连接URL，获取数据库地址IP
                ip = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
                ip = ip.substring(0, ip.indexOf(":"));

                // 解析数据库连接URL，获取数据库地址端口
                port = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
                port = port.substring(port.indexOf(":") + 1, port.indexOf("/"));

                break;
            case oracle:
                driverClass = "oracle.jdbc.driver.OracleDriver";

                // 解析数据库连接URL，获取数据库名称
                String[] prop = jdbcUrl.substring(jdbcUrl.indexOf("@") + 1).split(":");
                dbName = prop[2];

                // 解析数据库连接URL，获取数据库地址IP
                ip = prop[0];

                // 解析数据库连接URL，获取数据库地址端口
                port = prop[1];

                break;
            case sqlserver:
                driverClass = "net.sourceforge.jtds.jdbc.Driver";

                // 解析数据库连接URL，获取数据库名称
                dbName = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
                dbName = dbName.substring(dbName.indexOf("/") + 1);

                // 解析数据库连接URL，获取数据库地址IP
                ip = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
                ip = ip.substring(0, ip.indexOf(":"));

                // 解析数据库连接URL，获取数据库地址端口
                port = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
                port = port.substring(port.indexOf(":") + 1, port.indexOf("/"));

                break;
            case db2:
                driverClass = "com.ibm.db2.jcc.DB2Driver";

                // 解析数据库连接URL，获取数据库名称
                dbName = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
                dbName = dbName.substring(dbName.indexOf("/") + 1);

                // 解析数据库连接URL，获取数据库地址IP
                ip = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
                ip = ip.substring(0, ip.indexOf(":"));

                // 解析数据库连接URL，获取数据库地址端口
                port = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2);
                port = port.substring(port.indexOf(":") + 1, port.indexOf("/"));

                break;
        }

        // 把数据库连接信息写入常用map
        DataBase db = new DataBase();
        db.setDriverClass(driverClass);
        db.setJdbcUrl(jdbcUrl);
        db.setUserName(userName);
        db.setPassWord(passWord);

        db.setIp(ip);
        db.setPort(port);
        db.setDbName(dbName);

        return db;
    }

    /**
     * 数据库导出
     * @param exportPath
     * @throws IOException
     */
	/*public static void exportSql(String exportPath) throws IOException {
		DataBase db = getDbInfo(Driver.MYSQL);
		String username = db.getUserName();
		String password = db.getPassWord();
		String ip = db.getIp();
		String port = db.getPort();
		String database = db.getDbName();
		
		StringBuilder command = new StringBuilder();

		String db_type = PropKit.get(ConstantInit.db_type_key);
		if(db_type.equals(ConstantInit.db_type_postgresql)){ // pg
			// pg_dump --host 127.0.0.1 --port 5432 --username "postgres" --role "postgres" --no-password  --format custom --blobs --encoding UTF8 --verbose --file "D:/jfinaluibv2.backup" "jfinaluibv2"
			command.append(PathKit.getWebRootPath()).append("/WEB-INF/database/pg/bin/pg_dump ");
			command.append(" --host ").append(ip).append(" --port ").append(port).append(" --username ").append(" \"postgres\" ");
			command.append(" --role \"postgres\" --no-password  --format custom --blobs --encoding UTF8 --verbose --file ").append(exportPath).append(" \"").append(database).append("\" ");
			
		}else if(db_type.equals(ConstantInit.db_type_mysql)){ // mysql
			command.append("cmd /c mysqldump -u").append(username).append(" -p").append(password)//密码是用的小p，而端口是用的大P。  
					.append(" -h").append(ip).append(" -P").append(port).append(" ").append(database).append(" -r \"").append(exportPath+"\"");
			
		} else if(db_type.equals(ConstantInit.db_type_oracle)){ // oracle
			
		}
		
		try {
			Process process = Runtime.getRuntime().exec(command.toString(), null, new File(exportPath));
			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}*/

    /**
     * 数据库导入
     * @param filePath
     * @throws IOException
     */
	/*public static void importSql(String filePath) throws IOException {
		DataBase db = getDbInfo(Driver.MYSQL);
		String username = db.getUserName();
		String password = db.getPassWord();
		String ip = db.getIp();
		String port = db.getPort();
		String database = db.getDbName();
		
		String db_type = PropKit.get(ConstantInit.db_type_key);
		if(db_type.equals(ConstantInit.db_type_postgresql)){ // pg
			
			
			
		}else if(db_type.equals(ConstantInit.db_type_mysql)){ // mysql
			//第一步，获取登录命令语句  
			String loginCommand = new StringBuilder().append("mysql -u").append(username).append(" -p").append(password).append(" -h").append(ip).append(" -P").append(port).toString();
			//第二步，获取切换数据库到目标数据库的命令语句  
			String switchCommand = new StringBuilder("use ").append(database).toString();
			//第三步，获取导入的命令语句  
			String importCommand = new StringBuilder("source ").append(filePath).toString();
			//需要返回的命令语句数组  
			String[] commands = new String[] { loginCommand, switchCommand, importCommand };
			
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(commands[0]);
			//执行了第一条命令以后已经登录到mysql了，所以之后就是利用mysql的命令窗口  
			//进程执行后面的代码  
			OutputStream os = process.getOutputStream();
			OutputStreamWriter writer = new OutputStreamWriter(os);
			//命令1和命令2要放在一起执行  
			writer.write(commands[1] + "\r\n" + commands[2]);
			writer.flush();
			writer.close();
			os.close();
			
		} else if(db_type.equals(ConstantInit.db_type_oracle)){ // oracle

		}
	}*/
}

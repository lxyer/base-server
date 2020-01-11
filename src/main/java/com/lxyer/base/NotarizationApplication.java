package com.lxyer.base;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@SpringBootApplication
@MapperScan({"com.deepsec.notarization.modules.*.*.dao", "com.deepsec.notarization.modules.*.dao"})
public class NotarizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotarizationApplication.class, args);
    }

}

package edu.cdtu.hele_backend.service;

import edu.cdtu.hele_backend.entity.dto.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
* @author 31308
* @description 针对表【db_account】的数据库操作Service
* @createDate 2024-04-16 12:14:49
*/
public interface AccountService extends IService<Account>, UserDetailsService {

}

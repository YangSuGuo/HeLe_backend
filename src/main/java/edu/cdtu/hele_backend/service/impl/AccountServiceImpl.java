package edu.cdtu.hele_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.cdtu.hele_backend.entity.dto.Account;
import edu.cdtu.hele_backend.mapper.AccountMapper;
import edu.cdtu.hele_backend.service.AccountService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 31308
 * @description 针对表【db_account】的数据库操作Service实现
 * @createDate 2024-04-16 12:14:49
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
        implements AccountService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = findUserByUsernameOrEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException("用户名或邮箱错误");
        } else {
            return User.withUsername(username).password(account.getPassword()).roles(account.getRole()).build();
        }
    }

    /**
     * 通过用户名或邮件地址查找用户
     *
     * @param user 用户名或邮件
     * @return 账户实体
     */
    public Account findUserByUsernameOrEmail(String user) {
        return this.query()
                .eq("username", user).or()
                .eq("email", user)
                .one();
    }
}





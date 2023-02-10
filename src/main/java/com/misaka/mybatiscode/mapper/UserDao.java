package com.misaka.mybatiscode.mapper;

import com.misaka.mybatiscode.doman.User;

public interface UserDao {
    User selectById(int id);
}

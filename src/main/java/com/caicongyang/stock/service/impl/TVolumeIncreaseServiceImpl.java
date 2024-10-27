package com.caicongyang.stock.service.impl;

import com.caicongyang.stock.domain.TVolumeIncrease;
import com.caicongyang.stock.mapper.TVolumeIncreaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 成交量显著增加的股票表 服务实现类
 * </p>
 *
 * @author caicongyang
 * @since 2024-10-27
 */
@Service
public class TVolumeIncreaseServiceImpl extends ServiceImpl<TVolumeIncreaseMapper, TVolumeIncrease> implements ITVolumeIncreaseService {
    // 可以实现自定义的业务方法
}

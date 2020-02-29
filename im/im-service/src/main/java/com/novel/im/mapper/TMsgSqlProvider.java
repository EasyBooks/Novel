package com.novel.im.mapper;

import com.novel.im.domain.TMsg;
import com.novel.im.domain.TMsgExample.Criteria;
import com.novel.im.domain.TMsgExample.Criterion;
import com.novel.im.domain.TMsgExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class TMsgSqlProvider {

    public String countByExample(TMsgExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("t_msg");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(TMsgExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("t_msg");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(TMsg record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("t_msg");
        
        if (record.getSendId() != null) {
            sql.VALUES("send_id", "#{sendId,jdbcType=BIGINT}");
        }
        
        if (record.getAcceptId() != null) {
            sql.VALUES("accept_id", "#{acceptId,jdbcType=BIGINT}");
        }
        
        if (record.getMsg() != null) {
            sql.VALUES("msg", "#{msg,jdbcType=VARCHAR}");
        }
        
        if (record.getSignFlag() != null) {
            sql.VALUES("sign_flag", "#{signFlag,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String selectByExample(TMsgExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("send_id");
        sql.SELECT("accept_id");
        sql.SELECT("msg");
        sql.SELECT("sign_flag");
        sql.SELECT("create_time");
        sql.FROM("t_msg");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        TMsg record = (TMsg) parameter.get("record");
        TMsgExample example = (TMsgExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("t_msg");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=BIGINT}");
        }
        
        if (record.getSendId() != null) {
            sql.SET("send_id = #{record.sendId,jdbcType=BIGINT}");
        }
        
        if (record.getAcceptId() != null) {
            sql.SET("accept_id = #{record.acceptId,jdbcType=BIGINT}");
        }
        
        if (record.getMsg() != null) {
            sql.SET("msg = #{record.msg,jdbcType=VARCHAR}");
        }
        
        if (record.getSignFlag() != null) {
            sql.SET("sign_flag = #{record.signFlag,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("t_msg");
        
        sql.SET("id = #{record.id,jdbcType=BIGINT}");
        sql.SET("send_id = #{record.sendId,jdbcType=BIGINT}");
        sql.SET("accept_id = #{record.acceptId,jdbcType=BIGINT}");
        sql.SET("msg = #{record.msg,jdbcType=VARCHAR}");
        sql.SET("sign_flag = #{record.signFlag,jdbcType=INTEGER}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        
        TMsgExample example = (TMsgExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(TMsg record) {
        SQL sql = new SQL();
        sql.UPDATE("t_msg");
        
        if (record.getSendId() != null) {
            sql.SET("send_id = #{sendId,jdbcType=BIGINT}");
        }
        
        if (record.getAcceptId() != null) {
            sql.SET("accept_id = #{acceptId,jdbcType=BIGINT}");
        }
        
        if (record.getMsg() != null) {
            sql.SET("msg = #{msg,jdbcType=VARCHAR}");
        }
        
        if (record.getSignFlag() != null) {
            sql.SET("sign_flag = #{signFlag,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, TMsgExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}
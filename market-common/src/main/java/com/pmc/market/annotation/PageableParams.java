package com.pmc.market.annotation;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 김지애 / hirlawldo413@gmail.com
 * @since 2021/06/12
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "int", paramType = "query", defaultValue = "0", value = "페이지 번호(0..N)"),
        @ApiImplicitParam(name = "size", dataType = "int", paramType = "query", defaultValue = "20", value = "페이지 크기")
})
public @interface PageableParams {

}

package com.athena.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Tommy on 2017/9/2.
 *
 * Delegation of Copy since that Copy has been annotated by @MappedSuperClass
 */
@Entity
@Table(name = "copy")
public class SimpleCopy extends Copy {
}

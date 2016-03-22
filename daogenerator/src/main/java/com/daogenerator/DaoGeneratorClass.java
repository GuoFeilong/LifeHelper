package com.daogenerator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class DaoGeneratorClass {
    // java main method for create sql table
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(GreenDaoConstant.DATA_VERSION_CODE, GreenDaoConstant.PACKAGE_NAME);
        creatTable(schema);
        new DaoGenerator().generateAll(schema, GreenDaoConstant.DAO_PATH);
    }


    private static void creatTable(Schema schema) {
        Entity routeLineNodes = schema.addEntity(GreenDaoTableName.ROUTE_LINE_NODES_TABLE_NAME);
        routeLineNodes.implementsSerializable();
        routeLineNodes.addIdProperty().primaryKey().autoincrement();
        routeLineNodes.addIntProperty(RouteLinePlanNodesProperties.ROUTE_LINE_TYPE);

        Entity planNode = schema.addEntity(GreenDaoTableName.PLAN_NODE_TABLE_NAME);
        planNode.implementsSerializable();
        planNode.addIdProperty().primaryKey().autoincrement();
        planNode.addDoubleProperty(PlanNodeProperties.NODE_LATITUDE);
        planNode.addDoubleProperty(PlanNodeProperties.NODE_LONGITUDE);
        planNode.addStringProperty(PlanNodeProperties.NODE_ADDRESS);

        // fk primaryKey 1 to n
        Property typeID = planNode.addLongProperty("typeID").getProperty();
        planNode.addToOne(routeLineNodes, typeID);
        ToMany addToMany = routeLineNodes.addToMany(planNode, typeID);
        addToMany.setName("planNodes");

    }
}

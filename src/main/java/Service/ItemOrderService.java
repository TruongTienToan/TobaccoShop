package Service;

import Model.ItemOrder;
import Utilities.AppUtils;
import Utilities.CSVUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemOrderService implements IItemOrderService {
    private static ItemOrderService instanceItemOrder;

    public ItemOrderService() {

    }

    public static ItemOrderService instanceItemOrder() {
        if (instanceItemOrder == null) {
            instanceItemOrder = new ItemOrderService();
        }
        return instanceItemOrder;
    }

    @Override
    public List<ItemOrder> findAllItemOrder() {
        List<ItemOrder> ItemOrders = new ArrayList<>();
        List<String> notes = CSVUtils.readFile(AppUtils.PATH_ODERITEM);
        for (String note : notes) {
            ItemOrders.add(ItemOrder.parseItemOrder(note));
        }
        return ItemOrders;
    }

    @Override
    public void addItemOrder(ItemOrder newItemOrder) {
        List<ItemOrder> ItemOrders = findAllItemOrder();
        ItemOrders.add(newItemOrder);
        CSVUtils.writeFile(AppUtils.PATH_ODERITEM, ItemOrders);
    }

    @Override
    public void update(Long idOrder, Double price, Double grandTotal) {
        List<ItemOrder> ItemOrders = findAllItemOrder();
        for (ItemOrder itemOrder : ItemOrders) {
            if (itemOrder.getIdItemOrder().equals(idOrder)) {
                if (itemOrder.getPrice().equals(price)) {
                    itemOrder.setGrandTotal(grandTotal);
                    CSVUtils.writeFile(AppUtils.PATH_ODERITEM, ItemOrders);
                    break;
                }
            }
        }
    }

    @Override
    public ItemOrder getOrderItemById(Long id) {
        List<ItemOrder> ItemOrders = findAllItemOrder();
        for (ItemOrder itemOrder : ItemOrders) {
            if (itemOrder.getIdOrder().equals(id)) {
                return itemOrder;
            }
        }
        return null;
    }
}

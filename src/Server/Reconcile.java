package Server;

import Common.Offer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Goes over the offers and 'reconciles' what offers are valid, still very much in progress
 */
public class Reconcile {
    public static void main(String[] args) {
        // write yo
        OfferDataSource o = new OfferDataSource();
        List<Offer> list = o.offerSet();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++){
                if (i == j) {
                    continue;

                }
                Integer id1 = list.get(i).getId();
                Integer id2 = list.get(j).getId();
                String type1 = list.get(i).getOfferType();
                String type2 = list.get(j).getOfferType();
                String ou1 = list.get(i).getOUName();
                String ou2 = list.get(j).getOUName();
                String asset1 = list.get(i).getAssetName();
                String asset2 = list.get(j).getAssetName();
                Integer qty1 = list.get(i).getQuantity();
                Integer qty2 = list.get(j).getQuantity();
                Integer price1 = list.get(i).getCreditsEach();
                Integer price2 = list.get(j).getCreditsEach();

                if ((i < j) && (!type1.equals(type2)) && (!ou1.equals(ou2)) && (asset1.equals(asset2)) && (qty1.equals(qty2)) && (price1.equals(price2))) {
                    o.deleteOffer(id1);
                    o.deleteOffer(id2);
                    Offer tmp1 = list.get(i);
                    Offer tmp2 = list.get(j);
                    list.remove(i);
                    list.add(0, tmp1);
                    list.remove(j);
                    list.add(1, tmp2);
                    break;
                }

            }


        }
//        System.out.println(list.get(7).getOfferType());
        o.close();

//        Hashset<OU> ous = ...;

//        OU has creditbalance, quantity of each asset/ type of asset as ke;
//        HashMap<AssetName, Int> ouassets = ;
//        int credit = 0;
//        Hashset<Offer> alltrades;
//        Offer

    }
}

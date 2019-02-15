package stas.lines2019.game.pay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.pay.PurchaseObserver;
import com.badlogic.gdx.pay.Transaction;

import stas.lines2019.game.util.Constants;

public class MyPurchaseObserver implements PurchaseObserver {

    @Override
    public void handleInstall() {
        Gdx.app.log("IAP", "Installed");

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
//                updateGuiWhenPurchaseManInstalled(null);
            }
        });
    }

    @Override
    public void handleInstallError(final Throwable e) {
        Gdx.app.error("IAP", "Error when trying to install PurchaseManager", e);
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
//                updateGuiWhenPurchaseManInstalled(e.getMessage());
            }
        });
    }

    @Override
    public void handleRestore(final Transaction[] transactions) {
        if (transactions != null && transactions.length > 0)
            for (Transaction t : transactions) {
                handlePurchase(t, true);
            }
        else if (false)
            showErrorOnMainThread("Nothing to restore");
    }

    @Override
    public void handleRestoreError(Throwable e) {
        if (false)
            showErrorOnMainThread("Error restoring purchases: " + e.getMessage());
    }

    @Override
    public void handlePurchase(final Transaction transaction) {
        handlePurchase(transaction, false);
    }

    protected void handlePurchase(final Transaction transaction, final boolean fromRestore) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                if (transaction.isPurchased()) {
                    if (transaction.getIdentifier().equals(Constants.FRIEND_VERSION)) {

                    }
//                        buyEntitlement.setBought(fromRestore);
                }
            }
        });
    }

    @Override
    public void handlePurchaseError(Throwable e) {
        showErrorOnMainThread("Error on buying:\n" + e.getMessage());
    }

    @Override
    public void handlePurchaseCanceled() {

    }

    private void showErrorOnMainThread(final String message) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                // show a dialog here...
            }
        });
    }
}


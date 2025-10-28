let currentAccount = null;

function nextStep(step) {
    document.querySelectorAll(".step").forEach(s => s.classList.remove("active"));
    document.getElementById(`step${step}`).classList.add("active");
}

async function login() {
    const accountNumber = document.getElementById("accountNumber").value;
    const pin = document.getElementById("pin").value;
    const loginMessage = document.getElementById("loginMessage");

    // Validate account number length
    if (accountNumber.length !== 16) {
        loginMessage.innerText = "Please enter a valid 16-digit card number.";
        return;
    }

    try {
        const response = await fetch('/api/atm/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ accountNumber, pin })
        });

        if (response.ok) {
            currentAccount = await response.json();
            document.getElementById("userName").innerText = accountNumber;
            document.getElementById("balance").innerText = currentAccount.balance.toFixed(2);
            loginMessage.innerText = "";
            await loadWithdrawalHistory();
            nextStep(2);
        } else {
            loginMessage.innerText = "Invalid account number or PIN";
        }
    } catch (error) {
        loginMessage.innerText = "Login failed. Please try again.";
    }
}

async function checkBalance() {
    if (!currentAccount) return;

    try {
        const response = await fetch(`/api/atm/balance/${currentAccount.accountNumber}`);
        if (response.ok) {
            const balance = await response.json();
            document.getElementById("balance").innerText = balance.toFixed(2);
            alert(`Your balance is $${balance.toFixed(2)}`);
        }
    } catch (error) {
        alert("Failed to check balance.");
    }
}

function showDeposit() {
    nextStep(3);
}

async function deposit() {
    const amount = parseFloat(document.getElementById("depositAmount").value);
    const depositMessage = document.getElementById("depositMessage");

    if (!amount || amount <= 0) {
        depositMessage.innerText = "Please enter a valid amount.";
        return;
    }

    try {
        const response = await fetch(`/api/atm/deposit/${currentAccount.accountNumber}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ amount })
        });

        if (response.ok) {
            depositMessage.innerText = "Deposit successful!";
            document.getElementById("depositAmount").value = "";
            // Update balance
            const balanceResponse = await fetch(`/api/atm/balance/${currentAccount.accountNumber}`);
            if (balanceResponse.ok) {
                const balance = await balanceResponse.json();
                document.getElementById("balance").innerText = balance.toFixed(2);
            }
        } else {
            depositMessage.innerText = "Deposit failed.";
        }
    } catch (error) {
        depositMessage.innerText = "Deposit failed. Please try again.";
    }
}

function showWithdraw() {
    nextStep(4);
}

async function withdraw() {
    const amount = parseFloat(document.getElementById("withdrawAmount").value);
    const withdrawMessage = document.getElementById("withdrawMessage");

    if (!amount || amount <= 0) {
        withdrawMessage.innerText = "Please enter a valid amount.";
        return;
    }

    try {
        const response = await fetch(`/api/atm/withdraw/${currentAccount.accountNumber}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ amount })
        });

        if (response.ok) {
            withdrawMessage.innerText = "Withdrawal successful!";
            document.getElementById("withdrawAmount").value = "";
            // Update balance
            const balanceResponse = await fetch(`/api/atm/balance/${currentAccount.accountNumber}`);
            if (balanceResponse.ok) {
                const balance = await balanceResponse.json();
                document.getElementById("balance").innerText = balance.toFixed(2);
            }
        } else {
            const error = await response.text();
            depositMessage.innerText = error;
        }
    } catch (error) {
        withdrawMessage.innerText = "Withdrawal failed. Please try again.";
    }
}

function showSetLimit() {
    nextStep(5);
}

async function setLimit() {
    const dailyLimit = parseFloat(document.getElementById("dailyLimit").value);
    const limitMessage = document.getElementById("limitMessage");

    if (!dailyLimit || dailyLimit <= 0) {
        limitMessage.innerText = "Please enter a valid limit.";
        return;
    }

    try {
        const response = await fetch(`/api/atm/set-limit/${currentAccount.accountNumber}`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ dailyLimit })
        });

        if (response.ok) {
            limitMessage.innerText = "Daily limit set successfully!";
            document.getElementById("dailyLimit").value = "";
        } else {
            limitMessage.innerText = "Failed to set daily limit.";
        }
    } catch (error) {
        limitMessage.innerText = "Failed to set daily limit. Please try again.";
    }
}

function showTransactions() {
    nextStep(6);
    loadTransactions();
}

async function loadTransactions() {
    try {
        const response = await fetch(`/api/atm/transactions/${currentAccount.accountNumber}`);
        if (response.ok) {
            const transactions = await response.json();
            const transactionsDiv = document.getElementById("transactions");
            transactionsDiv.innerHTML = "<h3>Recent Transactions</h3>";
            if (transactions.length === 0) {
                transactionsDiv.innerHTML += "<p>No transactions found.</p>";
            } else {
                transactions.forEach(tx => {
                    transactionsDiv.innerHTML += `<p>${tx.type}: $${tx.amount.toFixed(2)} on ${new Date(tx.timestamp).toLocaleString()}</p>`;
                });
            }
        }
    } catch (error) {
        document.getElementById("transactions").innerHTML = "<p>Failed to load transactions.</p>";
    }
}

function backToMenu() {
    nextStep(2);
}

async function loadWithdrawalHistory() {
    try {
        const response = await fetch(`/api/atm/transactions/${currentAccount.accountNumber}`);
        if (response.ok) {
            const transactions = await response.json();
            const withdrawals = transactions.filter(tx => tx.type === 'WITHDRAWAL');
            const historyDiv = document.createElement('div');
            historyDiv.id = 'withdrawalHistory';
            historyDiv.innerHTML = '<h3>Old Withdrawals</h3>';
            if (withdrawals.length === 0) {
                historyDiv.innerHTML += '<p>No withdrawals found.</p>';
            } else {
                withdrawals.forEach(tx => {
                    historyDiv.innerHTML += `<p>Withdrew $${tx.amount.toFixed(2)} on ${new Date(tx.timestamp).toLocaleString()}</p>`;
                });
            }
            // Insert after balance
            const balanceP = document.querySelector('#step2 p');
            balanceP.insertAdjacentElement('afterend', historyDiv);
        }
    } catch (error) {
        console.error('Failed to load withdrawal history.');
    }
}

function logout() {
    currentAccount = null;
    document.getElementById("accountNumber").value = "";
    document.getElementById("pin").value = "";
    document.getElementById("loginMessage").innerText = "";
    // Remove withdrawal history on logout
    const historyDiv = document.getElementById('withdrawalHistory');
    if (historyDiv) historyDiv.remove();
    nextStep(1);
}
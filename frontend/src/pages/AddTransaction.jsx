import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../App.css";

const AddTransaction = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    title: "",
    amount: "",
    type: "EXPENSE",
    categoryId: "",
    date: new Date().toISOString().slice(0, 16),
    notes: "",
  });

  const [categories, setCategories] = useState([]);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const token = localStorage.getItem("token");

        const response = await axios.get("/api/categories", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

    console.log("Fetched categories:", response.data);

        setCategories(response.data);
      } catch (error) {
        console.error("Failed to load categories", error);
      }
    };

    fetchCategories();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const token = localStorage.getItem("token");

    if (token) {
      try {
        const payload = {
          ...formData,
          amount: parseFloat(formData.amount),
          date: new Date(formData.date).toISOString(),
        };

        await axios.post("/api/transactions", payload, {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        });

        alert("Transaction added successfully!");
        navigate("/dashboard");
      } catch (error) {
        console.error("Error adding transaction:", error);
        alert("Failed to add transaction.");
      }
    } else {
      alert("You must be logged in to add a transaction.");
    }
  };

  return (
    <div className="add-transaction-container">
      <h2 className="add-transaction-title">Add Transaction</h2>
      <form className="add-transaction-form" onSubmit={handleSubmit}>
        <input
          type="text"
          name="title"
          placeholder="Title"
          value={formData.title}
          onChange={handleChange}
          required
        />
        <input
          type="number"
          name="amount"
          placeholder="Amount"
          step="0.01"
          value={formData.amount}
          onChange={handleChange}
          required
        />
        <select name="type" value={formData.type} onChange={handleChange} required>
          <option value="INCOME">Income</option>
          <option value="EXPENSE">Expense</option>
        </select>
        <select name="categoryId" value={formData.categoryId} onChange={handleChange} required>
          <option value="">Select Category</option>
          {categories
            .filter((cat) => cat.type === formData.type)
            .map((cat) => (
              <option key={cat.id} value={cat.id}>
                {cat.name}
              </option>
            ))}
        </select>
        <input
          type="datetime-local"
          name="date"
          value={formData.date}
          onChange={handleChange}
          required
        />
        <textarea
          name="notes"
          placeholder="Notes (optional)"
          value={formData.notes}
          onChange={handleChange}
        />
        <button type="submit">Add Transaction</button>
      </form>
    </div>
  );
};

export default AddTransaction;

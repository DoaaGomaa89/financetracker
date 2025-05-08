import React, { useEffect, useState } from "react";
import axios from "axios";
import { Bar, Pie, Line } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  PointElement,
  LineElement,
} from "chart.js";
import "../App.css";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  PointElement,
  LineElement
);

function Dashboard() {
  const [data, setData] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (token) {
      axios
        .get("/api/dashboard", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((response) => setData(response.data))
        .catch((error) => console.error("Dashboard fetch error:", error));
    } else {
      console.error("No token found");
    }
  }, []);

  if (!data) return <div className="loading">Loading dashboard...</div>;

  const barData = {
    labels: ["Income", "Expenses"],
    datasets: [
      {
        label: "Total",
        data: [data.incomeVsExpenses.income, data.incomeVsExpenses.expenses],
        backgroundColor: ["#4caf50", "#f44336"],
      },
    ],
  };

  const pieData = {
    labels: data.expensesByCategory.labels,
    datasets: [
      {
        label: "Expenses by Category",
        data: data.expensesByCategory.values,
        backgroundColor: [
          "#FF6384", "#36A2EB", "#FFCE56", "#8BC34A", "#FF9800", "#9C27B0",
        ],
      },
    ],
  };

  const lineData = {
    labels: data.monthlyTrend.labels,
    datasets: [
      {
        label: "Income",
        data: data.monthlyTrend.income,
        borderColor: "#4caf50",
        fill: false,
      },
      {
        label: "Expenses",
        data: data.monthlyTrend.expenses,
        borderColor: "#f44336",
        fill: false,
      },
    ],
  };

  return (
    <div className="dashboard-container">
      <h2 className="dashboard-title">Dashboard</h2>

      <div className="chart-section">
        <h3 className="chart-title">Income vs Expenses</h3>
        <Bar data={barData} />
      </div>

      <div className="chart-section">
        <h3 className="chart-title">Expenses by Category</h3>
        <Pie data={pieData} />
      </div>

      <div className="chart-section">
        <h3 className="chart-title">Monthly Trend</h3>
        <Line data={lineData} />
      </div>
    </div>
  );
}

export default Dashboard;

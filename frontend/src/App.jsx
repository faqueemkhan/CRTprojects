import { useEffect, useState } from "react";
import axios from "axios";

function App() {

  // ================= BACKEND URL =================

  const backendUrl = "https://crtprojects-1.onrender.com";

  // ================= STATES =================

  const [patients, setPatients] = useState([]);

  const [patientName, setPatientName] = useState("");
  const [days, setDays] = useState("");

  const [message, setMessage] = useState("");

  // ================= FETCH PATIENTS =================

  const fetchPatients = async () => {

    try {

      const response =
        await axios.get(`${backendUrl}/patients`);

      setPatients(response.data);

    } catch (error) {

      console.log(error);
    }
  };

  // ================= ADD PATIENT =================

  const admitPatient = async () => {

    try {

     const totalFee = Number(days) * 700;

    const patient = {
       patientName: patientName,
       days: Number(days),
       totalFee: Number(totalFee),
      };

      await axios.post(
        `${backendUrl}/patients`,
        patient
      );

      setMessage("✅ Patient admitted successfully");

      fetchPatients();

      setPatientName("");
      setDays("");

    } catch (error) {

      console.log(error);

      setMessage("❌ Failed to admit patient");
    }
  };

  // ================= DELETE PATIENT =================

  const deletePatient = async (id) => {

    try {

      await axios.delete(
        `${backendUrl}/patients/${id}`
      );

      setMessage("🗑 Patient deleted successfully");

      fetchPatients();

    } catch (error) {

      console.log(error);
    }
  };

  // ================= LOAD PATIENTS =================

  useEffect(() => {

    fetchPatients();

  }, []);

  // ================= TOTAL REVENUE =================

  const totalRevenue =
    patients.reduce(
      (sum, patient) => sum + patient.totalFee,
      0
    );

  return (

    <div className="min-h-screen bg-slate-100 flex">

      {/* ================= SIDEBAR ================= */}

      <div className="w-72 bg-slate-900 text-white p-8 hidden md:block">

        <h1 className="text-3xl font-bold mb-12">
          🏥 Hospital
        </h1>

        <div className="space-y-6 text-lg">

          <p className="bg-blue-600 p-4 rounded-xl">
            Dashboard
          </p>

          <p className="hover:bg-slate-800 p-4 rounded-xl cursor-pointer">
            Patients
          </p>

          <p className="hover:bg-slate-800 p-4 rounded-xl cursor-pointer">
            Doctors
          </p>

          <p className="hover:bg-slate-800 p-4 rounded-xl cursor-pointer">
            Revenue
          </p>

        </div>

      </div>

      {/* ================= MAIN CONTENT ================= */}

      <div className="flex-1 p-8">

        {/* ================= HEADER ================= */}

        <div className="bg-gradient-to-r from-blue-700 to-indigo-700 text-white p-8 rounded-3xl shadow-2xl mb-10">

          <h1 className="text-5xl font-bold">
            Hospital Management Dashboard
          </h1>

          <p className="mt-3 text-lg text-slate-200">
            React + Spring Boot Hospital Management System
          </p>

        </div>

        {/* ================= STATS ================= */}

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-10">

          <div className="bg-white p-6 rounded-3xl shadow-lg">

            <h2 className="text-gray-500 font-semibold">
              Total Patients
            </h2>

            <p className="text-5xl font-bold mt-4 text-blue-600">
              {patients.length}
            </p>

          </div>

          <div className="bg-white p-6 rounded-3xl shadow-lg">

            <h2 className="text-gray-500 font-semibold">
              Revenue
            </h2>

            <p className="text-5xl font-bold mt-4 text-green-600">
              ₹{totalRevenue}
            </p>

          </div>

          <div className="bg-white p-6 rounded-3xl shadow-lg">

            <h2 className="text-gray-500 font-semibold">
              Database
            </h2>

            <p className="text-2xl font-bold mt-4 text-purple-600">
              Hospital Database System
            </p>

          </div>

        </div>

        {/* ================= CONTENT ================= */}

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">

          {/* ================= FORM ================= */}

          <div className="bg-white p-8 rounded-3xl shadow-xl">

            <h2 className="text-3xl font-bold mb-6">
              Add Patient
            </h2>

            <div className="space-y-5">

              <input
                type="text"
                placeholder="Patient Name"
                value={patientName}
                onChange={(e) =>
                  setPatientName(e.target.value)
                }
                className="w-full p-4 border rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
              />

              <input
                type="number"
                placeholder="Number of Days"
                value={days}
                onChange={(e) =>
                  setDays(e.target.value)
                }
                className="w-full p-4 border rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500"
              />

              <button
                onClick={admitPatient}
                className="w-full bg-blue-600 hover:bg-blue-700 text-white py-4 rounded-xl font-bold transition"
              >
                Admit Patient
              </button>

              {message && (

                <div className="bg-slate-100 p-4 rounded-xl text-center font-semibold">

                  {message}

                </div>
              )}

            </div>

          </div>

          {/* ================= TABLE ================= */}

          <div className="lg:col-span-2 bg-white p-8 rounded-3xl shadow-xl">

            <h2 className="text-3xl font-bold mb-6">
              Patient Records
            </h2>

            <div className="overflow-x-auto">

              <table className="w-full">

                <thead>

                  <tr className="bg-slate-100">

                    <th className="p-4 text-left">
                      ID
                    </th>

                    <th className="p-4 text-left">
                      Name
                    </th>

                    <th className="p-4 text-left">
                      Days
                    </th>

                    <th className="p-4 text-left">
                      Fee
                    </th>

                    <th className="p-4 text-left">
                      Action
                    </th>

                  </tr>

                </thead>

                <tbody>

                  {patients.map((patient) => (

                    <tr
                      key={patient.patientId}
                      className="border-b hover:bg-slate-50"
                    >

                      <td className="p-4 font-semibold text-blue-600">
                        #{patient.patientId}
                      </td>

                      <td className="p-4 font-semibold">
                        {patient.patientName}
                      </td>

                      <td className="p-4">
                        {patient.days}
                      </td>

                      <td className="p-4 text-green-600 font-bold">
                        ₹{patient.totalFee}
                      </td>

                      <td className="p-4">

                        <button
                          onClick={() =>
                            deletePatient(patient.patientId)
                          }
                          className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-xl transition"
                        >
                          Delete
                        </button>

                      </td>

                    </tr>

                  ))}

                </tbody>

              </table>

            </div>

          </div>

        </div>

      </div>

    </div>
  );
}

export default App;